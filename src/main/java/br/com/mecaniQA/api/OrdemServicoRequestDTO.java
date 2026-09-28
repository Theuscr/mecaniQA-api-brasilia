package br.com.mecaniQA.api;

public class OrdemServicoRequestDTO {
    private String placaVeiculo;
    private String descricaoProblema;

    public OrdemServicoRequestDTO() {}

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }
}