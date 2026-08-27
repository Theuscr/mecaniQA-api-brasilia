package br.com.mecaniQA.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pecas") // Rota RESTful exigida no documento
public class PecaController {

    // Repare que NÃO estamos usando @Autowired, como o professor proibiu.
    // Estamos acessando o repositório exclusivamente pelo getInstance() do Singleton.
    private PecaRepository repository = PecaRepository.getInstance();

    // US01: Cadastro de nova Peça
    @PostMapping
    public ResponseEntity<Peca> cadastrar(@RequestBody Peca peca) {
        Peca pecaSalva = repository.salvar(peca);
        // Retorna 201 Created conforme exigido para criação com sucesso
        return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalva);
    }

    // US02: Listar todas as peças cadastradas
    @GetMapping
    public ResponseEntity<List<Peca>> listarTodas() {
        // Retorna 200 OK
        return ResponseEntity.ok(repository.listarTodas());
    }

    // US02: Buscar uma peça específica pelo código
    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPorId(@PathVariable Long id) {
        Peca peca = repository.buscarPorId(id);
        if (peca != null) {
            return ResponseEntity.ok(peca); // 200 OK
        }
        // Se não encontrar o ID, retorna 404 Not Found, como o PDF exige
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // US03: Atualizar peça (preços e quantidade)
    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizar(@PathVariable Long id, @RequestBody Peca pecaAtualizada) {
        Peca pecaExistente = repository.buscarPorId(id);

        if (pecaExistente != null) {
            // Atualiza apenas os campos permitidos na User Story 03
            pecaExistente.setPrecoCusto(pecaAtualizada.getPrecoCusto());
            pecaExistente.setPrecoVenda(pecaAtualizada.getPrecoVenda());
            pecaExistente.setQuantidadeEmEstoque(pecaAtualizada.getQuantidadeEmEstoque());

            return ResponseEntity.ok(pecaExistente); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    // US04: Excluir peça do catálogo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean deletado = repository.deletar(id);
        if (deletado) {
            // Deleção bem sucedida retorna 204 No Content, conforme o PDF
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }
}