package br.com.mecaniQA.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoPecaController {

    private PedidoPecaRepository pedidoRepository = PedidoPecaRepository.getInstance();

    private PecaRepository pecaRepository = PecaRepository.getInstance();


    @PostMapping
    public ResponseEntity<PedidoPecaResponseDTO> criarPedido() {
        PedidoPeca novoPedido = new PedidoPeca();
        PedidoPeca pedidoSalvo = pedidoRepository.salvar(novoPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoPecaMapper.toDTO(pedidoSalvo));
    }


    @PostMapping("/{id}/itens")
    public ResponseEntity<PedidoPecaResponseDTO> adicionarPecaAoPedido(
            @PathVariable Long id,
            @RequestBody ItemPedidoRequestDTO requestDTO) {

        PedidoPeca pedidoExistente = pedidoRepository.buscarPorId(id);
        Peca pecaExistente = pecaRepository.buscarPorId(requestDTO.getPecaId());


        if (pedidoExistente != null && pecaExistente != null) {

            ItemPedidoPeca novoItem = new ItemPedidoPeca(pecaExistente, requestDTO.getQuantidade());


            pedidoExistente.getItens().add(novoItem);

            return ResponseEntity.ok(PedidoPecaMapper.toDTO(pedidoExistente));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoPecaResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido novoStatus) {

        PedidoPeca pedidoExistente = pedidoRepository.buscarPorId(id);

        if (pedidoExistente != null) {
            pedidoExistente.setStatus(novoStatus);

            return ResponseEntity.ok(PedidoPecaMapper.toDTO(pedidoExistente));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping
    public ResponseEntity<List<PedidoPecaResponseDTO>> listarTodos() {
        List<PedidoPeca> pedidos = pedidoRepository.listarTodos();
        List<PedidoPecaResponseDTO> dtos = new ArrayList<>();

        for (PedidoPeca pedido : pedidos) {
            dtos.add(PedidoPecaMapper.toDTO(pedido));
        }

        return ResponseEntity.ok(dtos);
    }
}