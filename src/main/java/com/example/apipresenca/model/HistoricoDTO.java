package com.example.apipresenca.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricoDTO {
    @JsonProperty("Nome")
    private String nomeAluno;
    @JsonProperty("Mes")
    private String mesReferencia;
    @JsonProperty("TotalAulas")
    private int totalAulas;
    @JsonProperty("Presença")
    private int presentes;
    @JsonProperty("Faltas")
    private int faltas;
    @JsonProperty("Percentual_Presença")
    private double percentual;

    public HistoricoDTO(HistoricoFrequencia h,  int totalAulas) {
        this.nomeAluno = h.getAluno().getNome();
        this.mesReferencia = h.getMesReferencia();
        this.totalAulas =  totalAulas;
        this.presentes = h.getTotalPresencas();
        this.faltas = h.getTotalFaltas();
        this.percentual = totalAulas == 0 ? 0 :
        BigDecimal.valueOf(h.getTotalPresencas() * 100.0 / totalAulas)
                 .setScale(2, RoundingMode.HALF_UP)
                 .doubleValue();
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(String mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public int getTotalAulas() {
        return totalAulas;
    }

    public void setTotalAulas(int totalAulas) {
        this.totalAulas = totalAulas;
    }

    public int getPresentes() {
        return presentes;
    }

    public void setPresentes(int presentes) {
        this.presentes = presentes;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }
}
