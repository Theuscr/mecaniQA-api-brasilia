package br.com.mecaniQA.api;
import java.time.LocalDateTime;
public class Servico {
    private long codigoUnico;
    private String nomeServico;
    private short tempoMinutos;
    private double custoTabelado;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public long getCodigoUnico() {
        return codigoUnico;
    }
    public void setCodigoUnico(long codigoUnico) {
        this.codigoUnico = codigoUnico;
    }
    public String getNomeServico() {
        return nomeServico;
    }
    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }
    public short getTempoMinutos() {
        return tempoMinutos;
    }
    public void setTempoMinutos(short tempoMinutos) {
        this.tempoMinutos = tempoMinutos;
    }
    public double getCustoTabelado() {
        return custoTabelado;
    }
    public void setCustoTabelado(double custoTabelado) {
        this.custoTabelado = custoTabelado;
    }
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
