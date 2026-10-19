package br.com.mecaniQA.api;

public class ConexaoBancoException extends PersistenciaException {

    public ConexaoBancoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}