package br.com.mecaniQA.api;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ItemPedidoPecaRowMapper {

    private ItemPedidoPecaRowMapper() {
    }

    public static ItemPedidoPeca mapear(ResultSet rs) throws SQLException {
        Peca peca = PecaRowMapper.mapear(rs);
        int quantidade = rs.getInt("quantidade_item");
        return new ItemPedidoPeca(peca, quantidade);
    }
}