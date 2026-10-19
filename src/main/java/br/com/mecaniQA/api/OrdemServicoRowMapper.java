package br.com.mecaniQA.api;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class OrdemServicoRowMapper {

    private OrdemServicoRowMapper() {
    }

    public static OrdemServico mapear(ResultSet rs) throws SQLException {
        return OrdemServico.builder()
                .comCodigo(rs.getLong("id"))
                .comPlaca(rs.getString("placa_veiculo"))
                .comDescricao(rs.getString("descricao_problema"))
                .comStatus(JdbcUtil.lerEnum(rs, "status", StatusOS.class))
                .comDataAbertura(JdbcUtil.lerDataHora(rs, "data_abertura"))
                .build();
    }
}