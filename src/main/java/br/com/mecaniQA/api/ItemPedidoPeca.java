package br.com.mecaniQA.api;

public class ItemPedidoPeca {
    private Peca peca;
    private int quantidade;


    public ItemPedidoPeca() {
    }


    public ItemPedidoPeca(Peca peca, int quantidade) {
        this.peca = peca;
        this.quantidade = quantidade;
    }


    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}