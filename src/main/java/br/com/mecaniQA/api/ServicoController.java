package br.com.mecaniQA.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos") // Rota RESTful para serviços
public class ServicoController {

    // Acessando o Singleton, sem usar @Autowired
    private ServicoRepository repository = ServicoRepository.getInstance();

    // US05: Cadastro de novo Serviço
    @PostMapping
    public ResponseEntity<Servico> cadastrar(@RequestBody Servico servico) {
        Servico servicoSalvo = repository.salvar(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo); // 201 Created
    }

    // US06: Listar catálogo de serviços
    @GetMapping
    public ResponseEntity<List<Servico>> listarTodos() {
        return ResponseEntity.ok(repository.listarTodos()); // 200 OK
    }

    // US06: Buscar serviço específico pelo código
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Long id) {
        Servico servico = repository.buscarPorId(id);
        if (servico != null) {
            return ResponseEntity.ok(servico); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    // US07: Atualizar serviço do catálogo (alterando tempo e custo)
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable Long id, @RequestBody Servico servicoAtualizado) {
        Servico servicoExistente = repository.buscarPorId(id);

        if (servicoExistente != null) {
            // Atualiza apenas os campos permitidos na User Story 07
            servicoExistente.setTempoMinutos(servicoAtualizado.getTempoMinutos());
            servicoExistente.setCustoTabelado(servicoAtualizado.getCustoTabelado());

            return ResponseEntity.ok(servicoExistente); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    // US08: Excluir serviço do catálogo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean deletado = repository.deletar(id);
        if (deletado) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }
}