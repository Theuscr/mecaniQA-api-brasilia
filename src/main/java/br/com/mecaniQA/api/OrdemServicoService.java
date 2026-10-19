package br.com.mecaniQA.api;

import java.util.ArrayList;
import java.util.List;

public class OrdemServicoService {

    private static OrdemServicoService instance;

    private OrdemServicoRepository repository = OrdemServicoRepository.getInstance();

    private OrdemServicoService() {
    }

    public static OrdemServicoService getInstance() {
        if (instance == null) {
            instance = new OrdemServicoService();
        }
        return instance;
    }

    public OrdemServicoResponseDTO criar(OrdemServicoRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new DadosInvalidosException("O corpo da requisição é obrigatório.");
        }
        if (requestDTO.getPlacaVeiculo() == null || requestDTO.getPlacaVeiculo().isBlank()) {
            throw new DadosInvalidosException("A placa do veículo é obrigatória.");
        }
        if (requestDTO.getDescricaoProblema() == null || requestDTO.getDescricaoProblema().isBlank()) {
            throw new DadosInvalidosException("A descrição do problema é obrigatória.");
        }

        OrdemServico novaOS = OrdemServicoMapper.toEntity(requestDTO);
        OrdemServico osSalva = repository.save(novaOS);
        return OrdemServicoMapper.toDTO(osSalva);
    }

    public OrdemServicoResponseDTO atualizarStatus(Long id, StatusOS novoStatus) {
        OrdemServico os = repository.findById(id).orElseThrow(() -> new OrdemServicoNaoEncontradaException(id));
        os.setStatus(novoStatus);
        OrdemServico atualizada = repository.update(os);
        return OrdemServicoMapper.toDTO(atualizada);
    }

    public List<OrdemServicoResponseDTO> listarTodas() {
        List<OrdemServicoResponseDTO> dtos = new ArrayList<>();
        for (OrdemServico os : repository.findAll()) {
            dtos.add(OrdemServicoMapper.toDTO(os));
        }
        return dtos;
    }
}