package com.example.apipresenca.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apipresenca.model.Aluno;
import com.example.apipresenca.model.Presenca;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    List<Presenca> findByAlunoId(Long alunoId);

    List<Presenca> findByAlunoAndDataBetween(Aluno aluno, LocalDate inicio, LocalDate fim);
}
