package com.example.apipresenca.service;
 
import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apipresenca.model.Aluno;
import com.example.apipresenca.model.HistoricoFrequencia;
import com.example.apipresenca.model.Presenca;
import com.example.apipresenca.repository.AlunoRepository;
import com.example.apipresenca.repository.HistoricoFrequenciaRepository;
import com.example.apipresenca.repository.PresencaRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class HistoricoFrequenciaService {

    @Autowired
    private PresencaRepository presencaRepository;

    @Autowired
    private HistoricoFrequenciaRepository historicoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public void gerarHistoricoMensal(int ano, int mes) {
        historicoRepository.deleteAll();
        
        String mesReferencia = YearMonth.of(ano, mes).toString();

        LocalDate inicio = YearMonth.of(ano, mes).atDay(1);
        LocalDate fim = YearMonth.of(ano, mes).atEndOfMonth();

        List<Aluno> alunos = alunoRepository.findAll();

        for (Aluno aluno : alunos) {
            List<Presenca> presencas = presencaRepository.findByAlunoAndDataBetween(aluno, inicio, fim);

            int totalPresencas = (int) presencas.stream().filter(Presenca::isPresente).count();
            int totalFaltas = (int) presencas.stream().filter(p -> !p.isPresente()).count();
            double percentual = presencas.isEmpty() ? 0 : (totalPresencas * 100.0 / presencas.size());

            HistoricoFrequencia historico = new HistoricoFrequencia();
            historico.setAluno(aluno);
            historico.setMesReferencia(mesReferencia);
            historico.setTotalPresencas(totalPresencas);
            historico.setTotalFaltas(totalFaltas);
            historico.setPercentualPresenca(percentual);
            historico.setDataGeracao(LocalDate.now());

            historicoRepository.save(historico);
        }
        
    }
 
    public int calcularTotalAulas(int ano, int mes) {
        YearMonth month = YearMonth.of(ano, mes);
        LocalDate inicio = month.atDay(1);
        LocalDate fim = month.atEndOfMonth();

        int totalAulas = 0;
        for (LocalDate date = inicio; !date.isAfter(fim); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();
            if (day == DayOfWeek.MONDAY || day == DayOfWeek.WEDNESDAY || day == DayOfWeek.FRIDAY) {
                totalAulas++;
            }
        }
        return totalAulas;
    }

    @SuppressWarnings("UseSpecificCatch")
    public byte[] gerarPdfDeStrings(List<String> linhas) {
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            for (String linha : linhas) {
                document.add(new Paragraph(linha, fontNormal));
            }

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    public List<String> getHistoricosPorMesFormatado(String mesReferencia) {
        List<HistoricoFrequencia> historicos = historicoRepository.findByMesReferencia(mesReferencia);

        return historicos.stream()
            .map(h -> String.format(
                "Aluno: %s | Mês: %s | Total Aulas: %d | Presentes: %d | Faltas: %d",
                h.getAluno().getNome(),
                h.getMesReferencia(),
                h.getTotalPresencas() + h.getTotalFaltas(),
                h.getTotalPresencas(),
                h.getTotalFaltas()
            ))
            .toList();
    }

    public byte[] gerarPdf(List<HistoricoFrequencia> historicos, int ano, int mes) {
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

        int totalAulas = calcularTotalAulas(ano, mes);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); Document document = new Document()) {
            PdfWriter.getInstance(document, baos);
            
            document.open();

            document.add(new Paragraph("Histórico de Frequência Mensal", fontTitulo));
            document.add(new Paragraph("Mês: " + mes , fontNormal));
            document.add(new Paragraph(" ")); 

            for (HistoricoFrequencia h : historicos) {
                double percentual = totalAulas == 0 ? 0 :
                    (h.getTotalPresencas() * 100.0 / totalAulas);

                String classificacao = calcularClassificacao(percentual);

                document.add(new Paragraph("Aluno: " + h.getAluno().getNome(), fontNormal));
                document.add(new Paragraph("Categoria: " + h.getAluno().getCategoria(), fontNormal));
                document.add(new Paragraph("Total de Aulas: " + totalAulas, fontNormal));
                document.add(new Paragraph("Presentes: " + h.getTotalPresencas(), fontNormal));
                document.add(new Paragraph("Faltas: " + h.getTotalFaltas(), fontNormal));
                document.add(new Paragraph(String.format("Percentual de Presença: %.2f%%", percentual), fontNormal));
                document.add(new Paragraph("Classificação: " + classificacao, fontNormal));
                document.add(new Paragraph(" "));
            }

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    private String calcularClassificacao(double percentual) {
    if (percentual >= 75) return "Excelente";
    if (percentual >= 50) return "Bom";
    if (percentual >= 30) return "Regular";
    return "Ruim";
}

    public List<HistoricoFrequencia> getHistoricosPorMes(String mesReferencia) {
        return historicoRepository.findByMesReferencia(mesReferencia);
    }
}
