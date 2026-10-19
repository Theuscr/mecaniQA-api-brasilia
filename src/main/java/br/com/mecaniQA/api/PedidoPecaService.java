package br.com.mecaniQA.api;

import java.util.ArrayList;
import java.util.List;

public class PedidoPecaService {

    private static PedidoPecaService instance;

    private PedidoPecaRepository pedidoRepository = PedidoPecaRepository.getInstance();
    private PecaRepository pecaRepository = PecaRepository.getInstance();

    private PedidoPecaService() {
    }

    public static PedidoPecaService getInstance() {
        if (instance == null) {
            instance = new PedidoPecaService();
        }
        return instance;
    }

    public PedidoPecaResponseDTO criarPedido() {
        PedidoPeca pedidoSalvo = pedidoRepository.save(new PedidoPeca());
        return PedidoPecaMapper.toDTO(pedidoSalvo);
    }

    public PedidoPecaResponseDTO adicionarPeca(Long pedidoId, ItemPedidoRequestDTO requestDTO) {
        if (requestDTO == null || requestDTO.getPecaId() == null) {
            throw new DadosInvalidosException("Informe o pecaId da peça a ser adicionada.");
        }
        if (requestDTO.getQuantidade() <= 0) {
            throw new DadosInvalidosException("A quantidade deve ser maior que zero.");
        }

        PedidoPeca pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
        Peca peca = pecaRepository.findById(requestDTO.getPecaId())
                .orElseThrow(() -> new PecaNaoEncontradaException(requestDTO.getPecaId()));

        ItemPedidoPeca novoItem = new ItemPedidoPeca(peca, requestDTO.getQuantidade());
        pedidoRepository.adicionarItem(pedidoId, novoItem);
        pedido.getItens().add(novoItem);

        return PedidoPecaMapper.toDTO(pedido);
    }

    public PedidoPecaResponseDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        PedidoPeca pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        pedido.setStatus(novoStatus);
        PedidoPeca atualizado = pedidoRepository.update(pedido);
        return PedidoPecaMapper.toDTO(atualizado);
    }

    public List<PedidoPecaResponseDTO> listarTodos() {
        List<PedidoPecaResponseDTO> dtos = new ArrayList<>();
        for (PedidoPeca pedido : pedidoRepository.findAll()) {
            dtos.add(PedidoPecaMapper.toDTO(pedido));
        }
        return dtos;
    }
}