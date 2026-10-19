package br.com.mecaniQA.api;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PedidoPecaRowMapper {

    private PedidoPecaRowMapper() {
    }

    public static PedidoPeca mapear(ResultSet rs) throws SQLException {
        PedidoPeca pedido = new PedidoPeca();
        pedido.setCodigoUnico(rs.getLong("id"));
        pedido.setStatus(JdbcUtil.lerEnum(rs, "status", StatusPedido.class));
        pedido.setDataCriacao(JdbcUtil.lerDataHora(rs, "data_criacao"));
        return pedido;
    }
}