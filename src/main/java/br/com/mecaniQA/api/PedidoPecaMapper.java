package br.com.mecaniQA.api;

import java.util.ArrayList;
import java.util.List;

public class PedidoPecaMapper {

    public static PedidoPecaResponseDTO toDTO(PedidoPeca pedido) {
        PedidoPecaResponseDTO dto = new PedidoPecaResponseDTO();
        dto.setCodigoUnico(pedido.getCodigoUnico());
        dto.setStatus(pedido.getStatus());


        List<ItemPedidoResponseDTO> itensDTO = new ArrayList<>();

        for (ItemPedidoPeca item : pedido.getItens()) {
            ItemPedidoResponseDTO itemDTO = new ItemPedidoResponseDTO();
            itemDTO.setPecaId(item.getPeca().getCodigoUnico());
            itemDTO.setNomePeca(item.getPeca().getNome());
            itemDTO.setQuantidade(item.getQuantidade());
            itensDTO.add(itemDTO);
        }

        dto.setItens(itensDTO);
        return dto;
    }
}