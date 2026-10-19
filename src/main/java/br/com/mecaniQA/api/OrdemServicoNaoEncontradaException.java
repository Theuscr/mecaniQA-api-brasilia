package br.com.mecaniQA.api;

public class OrdemServicoNaoEncontradaException extends RecursoNaoEncontradoException {

    public OrdemServicoNaoEncontradaException(Long id) {
        super("Ordem de Serviço não encontrada para o código: " + id);
    }
}