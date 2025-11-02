package com.example.apipresenca.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apipresenca.model.Aluno;
import com.example.apipresenca.model.Presenca;
import com.example.apipresenca.model.PresencaDTO;
import com.example.apipresenca.repository.AlunoRepository;
import com.example.apipresenca.repository.PresencaRepository;

@Service
public class PresencaService {

    @Autowired
    private PresencaRepository presencaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public Presenca registrarPresenca(Long alunoId, boolean presente) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Presenca p = new Presenca();
        p.setAluno(aluno);
        p.setData(LocalDate.now());
        p.setPresente(presente);

        return presencaRepository.save(p);
    }

    public List<Presenca> registrarLote(List<PresencaDTO> presencas) {
        List<Presenca> resultado = new ArrayList<>();
        for (PresencaDTO dto : presencas) {
            resultado.add(registrarPresenca(dto.getAlunoId(), dto.isPresente()));
        }
        return resultado;
    }

    public List<Presenca> listarPorAluno(Long alunoId) {
        return presencaRepository.findByAlunoId(alunoId);
    }
}
