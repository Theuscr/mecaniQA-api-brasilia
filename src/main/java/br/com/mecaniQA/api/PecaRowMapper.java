package br.com.mecaniQA.api;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class PecaRowMapper {

    private PecaRowMapper() {
    }


    public static String colunas(String alias) {
        String p = alias + ".";
        return p + "id, " + p + "nome, " + p + "codigo_barras, " + p + "fornecedor_marca, "
                + p + "quantidade_em_estoque, " + p + "preco_custo, " + p + "preco_venda, "
                + p + "data_cadastro, " + p + "data_ultima_atualizacao, " + p + "categoria, "
                + p + "tamanho, " + p + "cor";
    }

    public static Peca mapear(ResultSet rs) throws SQLException {
        Peca peca = new Peca();
        peca.setCodigoUnico(rs.getLong("id"));
        peca.setNome(rs.getString("nome"));
        peca.setCodigoBarras(rs.getString("codigo_barras"));
        peca.setFornecedorMarca(rs.getString("fornecedor_marca"));
        peca.setQuantidadeEmEstoque(rs.getInt("quantidade_em_estoque"));
        peca.setPrecoCusto(JdbcUtil.lerDouble(rs, "preco_custo"));
        peca.setPrecoVenda(JdbcUtil.lerDouble(rs, "preco_venda"));
        peca.setDataCadastro(JdbcUtil.lerDataHora(rs, "data_cadastro"));
        peca.setDataUltimaAtualizacao(JdbcUtil.lerDataHora(rs, "data_ultima_atualizacao"));
        peca.setCategoriaPeca(JdbcUtil.lerEnum(rs, "categoria", CategoriaPeca.class));
        peca.setTamanho(rs.getString("tamanho"));
        peca.setCor(rs.getString("cor"));
        return peca;
    }
}