package br.com.mecaniQA.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    private OrdemServicoService service = OrdemServicoService.getInstance();

    @PostMapping
    public ResponseEntity<?> criarOS(@RequestBody OrdemServicoRequestDTO requestDTO) {
        try {
            OrdemServicoResponseDTO responseDTO = service.criar(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // 201
        } catch (DadosInvalidosException e) {
            return RespostaHttp.requisicaoInvalida(e); // 400
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusOS novoStatus) {
        try {
            return ResponseEntity.ok(service.atualizarStatus(id, novoStatus)); // 200
        } catch (OrdemServicoNaoEncontradaException e) {
            return RespostaHttp.naoEncontrado(); // 404
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodas() {
        try {
            return ResponseEntity.ok(service.listarTodas()); // 200
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }
}