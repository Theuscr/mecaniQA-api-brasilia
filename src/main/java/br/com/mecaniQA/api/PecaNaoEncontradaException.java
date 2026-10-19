package br.com.mecaniQA.api;

public class PecaNaoEncontradaException extends RecursoNaoEncontradoException {

    public PecaNaoEncontradaException(Long id) {
        super("Peça não encontrada para o código: " + id);
    }
}