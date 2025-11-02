package com.example.apipresenca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apipresenca.model.Aluno;
import com.example.apipresenca.model.Presenca;
import com.example.apipresenca.model.PresencaDTO;
import com.example.apipresenca.service.PresencaService;
import com.example.apipresenca.service.AlunoService;

@RestController
@RequestMapping("/presencas")
@CrossOrigin("*")
public class PresencaController {

    @Autowired
    private PresencaService presencaService;

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/alunos")
        public List<Aluno> listarAlunos() {
            return alunoService.listarTodos();
    }

    @PostMapping("/registrarlote")
        public List<Presenca> registrarLote(@RequestBody List<PresencaDTO> presencas) {
            return presencaService.registrarLote(presencas);
    }

    @PostMapping("/{alunoId}")
        public Presenca registrar(@PathVariable Long alunoId, @RequestParam boolean presente) {
        return presencaService.registrarPresenca(alunoId, presente);
    }
    @GetMapping("/{alunoId}")
    public List<Presenca> listarPorAluno(@PathVariable Long alunoId) {
        return presencaService.listarPorAluno(alunoId);
    }
}
