package br.com.mecaniQA.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    private OrdemServicoRepository repository = OrdemServicoRepository.getInstance();


    @PostMapping
    public ResponseEntity<OrdemServicoResponseDTO> criarOS(@RequestBody OrdemServicoRequestDTO requestDTO) {

        OrdemServico novaOS = OrdemServicoMapper.toEntity(requestDTO);


        OrdemServico osSalva = repository.salvar(novaOS);


        OrdemServicoResponseDTO responseDTO = OrdemServicoMapper.toDTO(osSalva);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<OrdemServicoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusOS novoStatus) {

        OrdemServico osExistente = repository.buscarPorId(id);

        if (osExistente != null) {
            osExistente.setStatus(novoStatus);


            return ResponseEntity.ok(OrdemServicoMapper.toDTO(osExistente));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> listarTodas() {
        List<OrdemServico> ordens = repository.listarTodas();
        List<OrdemServicoResponseDTO> dtos = new ArrayList<>();

        for (OrdemServico os : ordens) {
            dtos.add(OrdemServicoMapper.toDTO(os));
        }

        return ResponseEntity.ok(dtos);
    }
}