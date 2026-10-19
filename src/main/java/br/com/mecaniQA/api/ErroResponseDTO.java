package br.com.mecaniQA.api;

public class ErroResponseDTO {
    private String erro;

    public ErroResponseDTO() {
    }

    public ErroResponseDTO(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}