package br.com.mecaniQA.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoPecaController {

    private PedidoPecaService service = PedidoPecaService.getInstance();

    @PostMapping
    public ResponseEntity<?> criarPedido() {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPedido()); // 201
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<?> adicionarPecaAoPedido(
            @PathVariable Long id,
            @RequestBody ItemPedidoRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(service.adicionarPeca(id, requestDTO)); // 200
        } catch (PedidoNaoEncontradoException | PecaNaoEncontradaException e) {
            return RespostaHttp.naoEncontrado(); // 404: pedido ou peça inexistente
        } catch (DadosInvalidosException e) {
            return RespostaHttp.requisicaoInvalida(e); // 400
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido novoStatus) {
        try {
            return ResponseEntity.ok(service.atualizarStatus(id, novoStatus)); // 200
        } catch (PedidoNaoEncontradoException e) {
            return RespostaHttp.naoEncontrado(); // 404
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(service.listarTodos()); // 200
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }
}