package br.com.mecaniQA.api;

public class PedidoNaoEncontradoException extends RecursoNaoEncontradoException {

    public PedidoNaoEncontradoException(Long id) {
        super("Pedido de Peças não encontrado para o código: " + id);
    }
}