package br.com.mecaniQA.api;

import java.util.List;

public class PedidoPecaResponseDTO {
    private Long codigoUnico;
    private StatusPedido status;
    private List<ItemPedidoResponseDTO> itens;

    public PedidoPecaResponseDTO() {}

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

    public List<ItemPedidoResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoResponseDTO> itens) {
        this.itens = itens;
    }
}