package br.com.mecaniQA.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoRepository extends AbstractJdbcRepository<OrdemServico, Long> {

    private static OrdemServicoRepository instance;

    private OrdemServicoRepository() {
    }

    public static OrdemServicoRepository getInstance() {
        if (instance == null) {
            instance = new OrdemServicoRepository();
        }
        return instance;
    }

    @Override
    protected String sqlInsert() {
        return "INSERT INTO ordem_servico (placa_veiculo, descricao_problema, status, data_abertura) "
                + "VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String sqlUpdate() {
        return "UPDATE ordem_servico SET placa_veiculo = ?, descricao_problema = ?, status = ?, "
                + "data_abertura = ? WHERE id = ?";
    }

    @Override
    protected String sqlSelectById() {
        return "SELECT id, placa_veiculo, descricao_problema, status, data_abertura "
                + "FROM ordem_servico WHERE id = ?";
    }

    @Override
    protected String sqlSelectAll() {
        return "SELECT id, placa_veiculo, descricao_problema, status, data_abertura "
                + "FROM ordem_servico ORDER BY id";
    }

    @Override
    protected String sqlDeleteById() {
        return "DELETE FROM ordem_servico WHERE id = ?";
    }

    @Override
    protected String sqlExistsById() {
        return "SELECT 1 FROM ordem_servico WHERE id = ?";
    }

    @Override
    protected void preencherInsert(PreparedStatement ps, OrdemServico os) throws SQLException {
        preencherCampos(ps, os);
    }

    @Override
    protected void preencherUpdate(PreparedStatement ps, OrdemServico os) throws SQLException {
        preencherCampos(ps, os);
        ps.setLong(5, os.getCodigoUnico());
    }

    @Override
    protected OrdemServico mapear(ResultSet rs) throws SQLException {
        return OrdemServicoRowMapper.mapear(rs);
    }

    @Override
    protected void definirId(OrdemServico os, long idGerado) {
        os.setCodigoUnico(idGerado);
    }


    @Override
    protected void aposInserir(Connection conexao, OrdemServico os) throws SQLException {
        String sqlServico = "INSERT INTO ordem_servico_servico (ordem_servico_id, servico_id) VALUES (?, ?)";
        for (Servico servico : os.getServicos()) {
            try (PreparedStatement ps = conexao.prepareStatement(sqlServico)) {
                ps.setLong(1, os.getCodigoUnico());
                ps.setLong(2, servico.getCodigoUnico());
                ps.executeUpdate();
            }
        }

        String sqlPeca = "INSERT INTO ordem_servico_peca (ordem_servico_id, peca_id, quantidade) VALUES (?, ?, ?)";
        for (ItemPedidoPeca item : os.getPecasUtilizadas()) {
            try (PreparedStatement ps = conexao.prepareStatement(sqlPeca)) {
                ps.setLong(1, os.getCodigoUnico());
                ps.setLong(2, item.getPeca().getCodigoUnico());
                ps.setInt(3, item.getQuantidade());
                ps.executeUpdate();
            }
        }
    }


    @Override
    protected void aposCarregar(Connection conexao, OrdemServico os) throws SQLException {
        List<Servico> servicos = new ArrayList<>();
        String sqlServicos = "SELECT " + ServicoRowMapper.colunas("s") + " FROM ordem_servico_servico oss "
                + "JOIN servico s ON s.id = oss.servico_id WHERE oss.ordem_servico_id = ? ORDER BY oss.id";
        try (PreparedStatement ps = conexao.prepareStatement(sqlServicos)) {
            ps.setLong(1, os.getCodigoUnico());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    servicos.add(ServicoRowMapper.mapear(rs));
                }
            }
        }
        os.setServicos(servicos);

        List<ItemPedidoPeca> pecas = new ArrayList<>();
        String sqlPecas = "SELECT osp.quantidade AS quantidade_item, " + PecaRowMapper.colunas("p")
                + " FROM ordem_servico_peca osp JOIN peca p ON p.id = osp.peca_id "
                + "WHERE osp.ordem_servico_id = ? ORDER BY osp.id";
        try (PreparedStatement ps = conexao.prepareStatement(sqlPecas)) {
            ps.setLong(1, os.getCodigoUnico());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pecas.add(ItemPedidoPecaRowMapper.mapear(rs));
                }
            }
        }
        os.setPecasUtilizadas(pecas);
    }

    private void preencherCampos(PreparedStatement ps, OrdemServico os) throws SQLException {
        ps.setString(1, os.getPlacaVeiculo());
        ps.setString(2, os.getDescricaoProblema());
        JdbcUtil.gravarEnum(ps, 3, os.getStatus());
        JdbcUtil.gravarDataHora(ps, 4, os.getDataAbertura());
    }
}