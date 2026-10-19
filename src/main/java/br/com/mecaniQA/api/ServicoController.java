package br.com.mecaniQA.api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
    private ServicoService service = ServicoService.getInstance();

    // US05: Cadastro de novo Serviço
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Servico servico) {
        try {
            Servico servicoSalvo = service.cadastrar(servico);
            return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo); // 201
        } catch (DadosInvalidosException e) {
            return RespostaHttp.requisicaoInvalida(e); // 400
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    // US06: Listar catálogo de serviços
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(service.listarTodos()); // 200
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    // US06: Buscar serviço pelo código
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id)); // 200
        } catch (ServicoNaoEncontradoException e) {
            return RespostaHttp.naoEncontrado(); // 404
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    // US07: Atualizar serviço (tempo e custo)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Servico servicoAtualizado) {
        try {
            return ResponseEntity.ok(service.atualizar(id, servicoAtualizado)); // 200
        } catch (ServicoNaoEncontradoException e) {
            return RespostaHttp.naoEncontrado(); // 404
        } catch (DadosInvalidosException e) {
            return RespostaHttp.requisicaoInvalida(e); // 400
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }

    // US08: Excluir serviço do catálogo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204
        } catch (ServicoNaoEncontradoException e) {
            return RespostaHttp.naoEncontrado(); // 404
        } catch (DadosInvalidosException e) {
            return RespostaHttp.requisicaoInvalida(e); // 400
        } catch (PersistenciaException e) {
            return RespostaHttp.erroInterno(e); // 500
        }
    }
}