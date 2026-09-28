package br.com.mecaniQA.api;

public class ItemPedidoResponseDTO {
    private Long pecaId;
    private String nomePeca;
    private int quantidade;

    public ItemPedidoResponseDTO() {}

    public Long getPecaId() {
        return pecaId;
    }

    public void setPecaId(Long pecaId) {
        this.pecaId = pecaId;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
