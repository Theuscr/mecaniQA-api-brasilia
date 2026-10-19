package br.com.mecaniQA.api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {
    private PecaService service = PecaService.getInstance();

    // US01: Cadastro de nova Peça
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Peca peca) {
        try {
            Peca pecaSalva = service.cadastrar(peca);
            return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalva); // 201
        } catch (DadosInvalidosException e) {
            return RespostaHttp.requisicaoInvalida(e); // 400
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    // US02: Listar todas as peças
    @GetMapping
    public ResponseEntity<?> listarTodas() {
        try {
            return ResponseEntity.ok(service.listarTodas()); // 200
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    // US02: Buscar uma peça pelo código
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id)); // 200
        } catch (PecaNaoEncontradaException e) {
            return RespostaHttp.naoEncontrado(); // 404
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    // US03: Atualizar peça (preços e quantidade)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Peca pecaAtualizada) {
        try {
            return ResponseEntity.ok(service.atualizar(id, pecaAtualizada)); // 200
        } catch (PecaNaoEncontradaException e) {
            return RespostaHttp.naoEncontrado(); // 404
        } catch (DadosInvalidosException e) {
            return RespostaHttp.requisicaoInvalida(e); // 400
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    // US04: Excluir peça do catálogo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204
        } catch (PecaNaoEncontradaException e) {
            return RespostaHttp.naoEncontrado(); // 404
        } catch (DadosInvalidosException e) {
            return RespostaHttp.requisicaoInvalida(e); // 400 (ex.: peça usada em um pedido)
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }
}