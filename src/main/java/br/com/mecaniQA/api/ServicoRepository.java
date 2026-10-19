package br.com.mecaniQA.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicoRepository extends AbstractJdbcRepository<Servico, Long> {

    private static ServicoRepository instance;

    private ServicoRepository() {
    }

    public static ServicoRepository getInstance() {
        if (instance == null) {
            instance = new ServicoRepository();
        }
        return instance;
    }

    @Override
    protected String sqlInsert() {
        return "INSERT INTO servico (nome_servico, tempo_minutos, custo_tabelado, data_criacao, data_atualizacao) "
                + "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String sqlUpdate() {
        return "UPDATE servico SET nome_servico = ?, tempo_minutos = ?, custo_tabelado = ?, "
                + "data_criacao = ?, data_atualizacao = ? WHERE id = ?";
    }

    @Override
    protected String sqlSelectById() {
        return "SELECT " + ServicoRowMapper.colunas("s") + " FROM servico s WHERE s.id = ?";
    }

    @Override
    protected String sqlSelectAll() {
        return "SELECT " + ServicoRowMapper.colunas("s") + " FROM servico s ORDER BY s.id";
    }

    @Override
    protected String sqlDeleteById() {
        return "DELETE FROM servico WHERE id = ?";
    }

    @Override
    protected String sqlExistsById() {
        return "SELECT 1 FROM servico WHERE id = ?";
    }

    @Override
    protected void preencherInsert(PreparedStatement ps, Servico servico) throws SQLException {
        preencherCampos(ps, servico);
    }

    @Override
    protected void preencherUpdate(PreparedStatement ps, Servico servico) throws SQLException {
        preencherCampos(ps, servico);
        ps.setLong(6, servico.getCodigoUnico());
    }

    @Override
    protected Servico mapear(ResultSet rs) throws SQLException {
        return ServicoRowMapper.mapear(rs);
    }

    @Override
    protected void definirId(Servico servico, long idGerado) {
        servico.setCodigoUnico(idGerado);
    }

    private void preencherCampos(PreparedStatement ps, Servico servico) throws SQLException {
        ps.setString(1, servico.getNomeServico());
        ps.setShort(2, servico.getTempoMinutos());
        ps.setDouble(3, servico.getCustoTabelado());
        JdbcUtil.gravarDataHora(ps, 4, servico.getDataCriacao());
        JdbcUtil.gravarDataHora(ps, 5, servico.getDataAtualizacao());
    }
}