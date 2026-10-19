package br.com.mecaniQA.api;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ServicoRowMapper {

    private ServicoRowMapper() {
    }

    public static String colunas(String alias) {
        String s = alias + ".";
        return s + "id, " + s + "nome_servico, " + s + "tempo_minutos, " + s + "custo_tabelado, "
                + s + "data_criacao, " + s + "data_atualizacao";
    }

    public static Servico mapear(ResultSet rs) throws SQLException {
        Servico servico = new Servico();
        servico.setCodigoUnico(rs.getLong("id"));
        servico.setNomeServico(rs.getString("nome_servico"));
        servico.setTempoMinutos(rs.getShort("tempo_minutos"));
        servico.setCustoTabelado(rs.getDouble("custo_tabelado"));
        servico.setDataCriacao(JdbcUtil.lerDataHora(rs, "data_criacao"));
        servico.setDataAtualizacao(JdbcUtil.lerDataHora(rs, "data_atualizacao"));
        return servico;
    }
}