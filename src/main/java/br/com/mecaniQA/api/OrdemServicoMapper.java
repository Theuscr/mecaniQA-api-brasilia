package br.com.mecaniQA.api;

public class OrdemServicoMapper {


    public static OrdemServico toEntity(OrdemServicoRequestDTO dto) {
        return new OrdemServico.OrdemServicoBuilder()
                .comPlaca(dto.getPlacaVeiculo())
                .comDescricao(dto.getDescricaoProblema())
                .build();
    }


    public static OrdemServicoResponseDTO toDTO(OrdemServico entidade) {
        OrdemServicoResponseDTO dto = new OrdemServicoResponseDTO();
        dto.setCodigoUnico(entidade.getCodigoUnico());
        dto.setPlacaVeiculo(entidade.getPlacaVeiculo());
        dto.setDescricaoProblema(entidade.getDescricaoProblema());
        dto.setStatus(entidade.getStatus());
        return dto;
    }
}