package com.example.apipresenca.model;

public class AlunoDTO {
    private final Long id;
    private final String nome;
    private final int idade;
    private final String categoria;
    private final String telefone;
    private final String dataEntrada;

    public AlunoDTO(Aluno aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.idade = aluno.getIdade();
        this.categoria = aluno.getCategoria();
        this.telefone = aluno.getTelefone();
        this.dataEntrada = aluno.getDataEntrada().toString();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getCategoria() { return categoria; }
    public String getTelefone() { return telefone; }
    public String getDataEntrada() { return dataEntrada; }
}
