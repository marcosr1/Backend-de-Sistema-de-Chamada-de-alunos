package com.example.apipresenca.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apipresenca.model.Aluno;
import com.example.apipresenca.model.AlunoDTO;
import com.example.apipresenca.service.AlunoService;

@RestController
@RequestMapping("/alunos")
@CrossOrigin("*")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping
    public Aluno criarAluno(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    @GetMapping
    public List<AlunoDTO> listarAlunos() {
        return alunoService.listarTodos().stream()
                .map(AlunoDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public Aluno buscarAluno(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Aluno> alunoOptional = Optional.ofNullable(alunoService.buscarPorId(id));
        if (alunoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Aluno aluno = alunoOptional.get();

        updates.forEach((campo, valor) -> {
            switch (campo) {
                case "nome" -> aluno.setNome((String) valor);
                case "idade" -> aluno.setIdade((Integer) valor);
                case "categoria" -> aluno.setCategoria((String) valor);
                case "telefone" -> aluno.setTelefone((String) valor);
                case "dataEntrada" -> aluno.setDataEntrada(LocalDate.parse((String) valor));
            }
        });

        Aluno atualizado = alunoService.salvar(aluno);
        return ResponseEntity.ok(new AlunoDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public void deletarAluno(@PathVariable Long id) {
        alunoService.excluir(id);
    }
}

