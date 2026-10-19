package br.com.mecaniQA.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoPecaRepository extends AbstractJdbcRepository<PedidoPeca, Long> {

    private static PedidoPecaRepository instance;

    private PedidoPecaRepository() {
    }

    public static PedidoPecaRepository getInstance() {
        if (instance == null) {
            instance = new PedidoPecaRepository();
        }
        return instance;
    }

    @Override
    protected String sqlInsert() {
        return "INSERT INTO pedido_peca (status, data_criacao) VALUES (?, ?)";
    }

    @Override
    protected String sqlUpdate() {
        return "UPDATE pedido_peca SET status = ?, data_criacao = ? WHERE id = ?";
    }

    @Override
    protected String sqlSelectById() {
        return "SELECT id, status, data_criacao FROM pedido_peca WHERE id = ?";
    }

    @Override
    protected String sqlSelectAll() {
        return "SELECT id, status, data_criacao FROM pedido_peca ORDER BY id";
    }

    @Override
    protected String sqlDeleteById() {
        return "DELETE FROM pedido_peca WHERE id = ?";
    }

    @Override
    protected String sqlExistsById() {
        return "SELECT 1 FROM pedido_peca WHERE id = ?";
    }

    @Override
    protected void preencherInsert(PreparedStatement ps, PedidoPeca pedido) throws SQLException {
        JdbcUtil.gravarEnum(ps, 1, pedido.getStatus());
        JdbcUtil.gravarDataHora(ps, 2, pedido.getDataCriacao());
    }

    @Override
    protected void preencherUpdate(PreparedStatement ps, PedidoPeca pedido) throws SQLException {
        preencherInsert(ps, pedido);
        ps.setLong(3, pedido.getCodigoUnico());
    }

    @Override
    protected PedidoPeca mapear(ResultSet rs) throws SQLException {
        return PedidoPecaRowMapper.mapear(rs);
    }

    @Override
    protected void definirId(PedidoPeca pedido, long idGerado) {
        pedido.setCodigoUnico(idGerado);
    }


    @Override
    protected void aposInserir(Connection conexao, PedidoPeca pedido) throws SQLException {
        for (ItemPedidoPeca item : pedido.getItens()) {
            inserirItem(conexao, pedido.getCodigoUnico(), item);
        }
    }


    @Override
    protected void aposCarregar(Connection conexao, PedidoPeca pedido) throws SQLException {
        List<ItemPedidoPeca> itens = new ArrayList<>();
        String sql = "SELECT i.quantidade AS quantidade_item, " + PecaRowMapper.colunas("p")
                + " FROM item_pedido_peca i JOIN peca p ON p.id = i.peca_id "
                + "WHERE i.pedido_id = ? ORDER BY i.id";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, pedido.getCodigoUnico());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    itens.add(ItemPedidoPecaRowMapper.mapear(rs));
                }
            }
        }
        pedido.setItens(itens);
    }


    public void adicionarItem(Long pedidoId, ItemPedidoPeca item) {
        executarEmTransacao(conexao -> {
            inserirItem(conexao, pedidoId, item);
            return null;
        });
    }

    private void inserirItem(Connection conexao, Long pedidoId, ItemPedidoPeca item) throws SQLException {
        String sql = "INSERT INTO item_pedido_peca (pedido_id, peca_id, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, pedidoId);
            ps.setLong(2, item.getPeca().getCodigoUnico());
            ps.setInt(3, item.getQuantidade());
            ps.executeUpdate();
        }
    }
}