package br.com.mecaniQA.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemServico {
    private Long codigoUnico;
    private String placaVeiculo;
    private String descricaoProblema;
    private StatusOS status;
    private List<Servico> servicos;
    private List<ItemPedidoPeca> pecasUtilizadas;
    private LocalDateTime dataAbertura;


    private OrdemServico(OrdemServicoBuilder builder) {
        this.codigoUnico = builder.codigoUnico;
        this.placaVeiculo = builder.placaVeiculo;
        this.descricaoProblema = builder.descricaoProblema;
        this.status = builder.status;
        this.servicos = builder.servicos;
        this.pecasUtilizadas = builder.pecasUtilizadas;
        this.dataAbertura = builder.dataAbertura;
    }


    public static class OrdemServicoBuilder {
        private Long codigoUnico;
        private String placaVeiculo;
        private String descricaoProblema;
        private StatusOS status = StatusOS.ABERTO;
        private List<Servico> servicos = new ArrayList<>();
        private List<ItemPedidoPeca> pecasUtilizadas = new ArrayList<>();
        private LocalDateTime dataAbertura = LocalDateTime.now();


        public OrdemServicoBuilder comCodigo(Long codigoUnico) {
            this.codigoUnico = codigoUnico;
            return this;
        }

        public OrdemServicoBuilder comPlaca(String placaVeiculo) {
            this.placaVeiculo = placaVeiculo;
            return this;
        }

        public OrdemServicoBuilder comDescricao(String descricaoProblema) {
            this.descricaoProblema = descricaoProblema;
            return this;
        }

        public OrdemServicoBuilder comStatus(StatusOS status) {
            this.status = status;
            return this;
        }

        public OrdemServicoBuilder adicionarServico(Servico servico) {
            this.servicos.add(servico);
            return this;
        }

        public OrdemServicoBuilder adicionarPeca(ItemPedidoPeca item) {
            this.pecasUtilizadas.add(item);
            return this;
        }


        public OrdemServico build() {
            return new OrdemServico(this);
        }
    }


    public Long getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(Long codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

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

    public StatusOS getStatus() {
        return status;
    }

    public void setStatus(StatusOS status) {
        this.status = status;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<ItemPedidoPeca> getPecasUtilizadas() {
        return pecasUtilizadas;
    }

    public void setPecasUtilizadas(List<ItemPedidoPeca> pecasUtilizadas) {
        this.pecasUtilizadas = pecasUtilizadas;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
}