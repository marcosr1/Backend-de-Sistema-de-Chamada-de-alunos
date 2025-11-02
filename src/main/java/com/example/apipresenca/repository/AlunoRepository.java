package com.example.apipresenca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apipresenca.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {}
