package br.com.mecaniQA.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoPeca {
    private Long codigoUnico;
    private StatusPedido status;
    private List<ItemPedidoPeca> itens;
    private LocalDateTime dataCriacao;

    public PedidoPeca() {
        this.itens = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPedido.ORCANDO; // Status inicial padrão
    }

    public Long getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(Long codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public List<ItemPedidoPeca> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoPeca> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}