package com.example.apipresenca.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apipresenca.model.HistoricoDTO;
import com.example.apipresenca.model.HistoricoFrequencia;
import com.example.apipresenca.service.HistoricoFrequenciaService;
import com.lowagie.text.DocumentException;


@RestController
@RequestMapping("/historico")
@CrossOrigin("*")
public class HistoricoFrequenciaController {

    @Autowired
    private HistoricoFrequenciaService historicoService;

    @GetMapping("/")
    public List<HistoricoDTO> listarHistoricos(@RequestParam int ano, @RequestParam int mes) {
        String mesReferencia = String.format("%04d-%02d", ano, mes);
        List<HistoricoFrequencia> historicos = historicoService.getHistoricosPorMes(mesReferencia);

        int totalAulas = historicoService.calcularTotalAulas(ano, mes);

        return historicos.stream()
                .map(h -> new HistoricoDTO(h, totalAulas))
                .collect(Collectors.toList());
    }

    @PostMapping("/gerar")
    public ResponseEntity<String> gerarHistorico(@RequestParam int ano, @RequestParam int mes) {
        historicoService.gerarHistoricoMensal(ano, mes);
        return ResponseEntity.ok("Histórico de frequência gerado com sucesso!");
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> gerarPdfHistorico(@RequestParam int ano, @RequestParam int mes) throws DocumentException {
        String mesReferencia = String.format("%04d-%02d", ano, mes);

        List<HistoricoFrequencia> historicos = historicoService.getHistoricosPorMes(mesReferencia);

        byte[] pdfBytes = historicoService.gerarPdf(historicos, ano, mes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename("historico.pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
