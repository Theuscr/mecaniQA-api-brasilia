package br.com.mecaniQA.api;

public class ServicoNaoEncontradoException extends RecursoNaoEncontradoException {

    public ServicoNaoEncontradoException(Long id) {
        super("Serviço não encontrado para o código: " + id);
    }
}