package com.example.apipresenca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apipresenca.model.HistoricoFrequencia;

@Repository
public interface HistoricoFrequenciaRepository extends JpaRepository<HistoricoFrequencia, Long> {

    public List<HistoricoFrequencia> findByMesReferencia(String mesReferencia);
 }
