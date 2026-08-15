package br.com.mecaniQA.api;

import java.time.LocalDateTime;

public class Peca {
    private Long codigoUnico;
    private String nome;
    private String codigoBarras;
    private String fornecedorMarca;
    private int quantidadeEmEstoque;
    private Double precoCusto;
    private Double precoVenda;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaAtualizacao;
    private CategoriaPeca categoriaPeca;
}
