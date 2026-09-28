package br.com.mecaniQA.api;

public class ItemPedidoRequestDTO {
    private Long pecaId;
    private int quantidade;

    public ItemPedidoRequestDTO() {}

    public Long getPecaId() {
        return pecaId;
    }

    public void setPecaId(Long pecaId) {
        this.pecaId = pecaId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}