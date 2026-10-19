package br.com.mecaniQA.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecaRepository extends AbstractJdbcRepository<Peca, Long> {

    private static PecaRepository instance;

    private PecaRepository() {
    }

    public static PecaRepository getInstance() {
        if (instance == null) {
            instance = new PecaRepository();
        }
        return instance;
    }

    @Override
    protected String sqlInsert() {
        return "INSERT INTO peca (nome, codigo_barras, fornecedor_marca, quantidade_em_estoque, "
                + "preco_custo, preco_venda, data_cadastro, data_ultima_atualizacao, categoria, tamanho, cor) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String sqlUpdate() {
        return "UPDATE peca SET nome = ?, codigo_barras = ?, fornecedor_marca = ?, quantidade_em_estoque = ?, "
                + "preco_custo = ?, preco_venda = ?, data_cadastro = ?, data_ultima_atualizacao = ?, "
                + "categoria = ?, tamanho = ?, cor = ? WHERE id = ?";
    }

    @Override
    protected String sqlSelectById() {
        return "SELECT " + PecaRowMapper.colunas("p") + " FROM peca p WHERE p.id = ?";
    }

    @Override
    protected String sqlSelectAll() {
        return "SELECT " + PecaRowMapper.colunas("p") + " FROM peca p ORDER BY p.id";
    }

    @Override
    protected String sqlDeleteById() {
        return "DELETE FROM peca WHERE id = ?";
    }

    @Override
    protected String sqlExistsById() {
        return "SELECT 1 FROM peca WHERE id = ?";
    }

    @Override
    protected void preencherInsert(PreparedStatement ps, Peca peca) throws SQLException {
        preencherCampos(ps, peca);
    }

    @Override
    protected void preencherUpdate(PreparedStatement ps, Peca peca) throws SQLException {
        preencherCampos(ps, peca);
        ps.setLong(12, peca.getCodigoUnico());
    }

    @Override
    protected Peca mapear(ResultSet rs) throws SQLException {
        return PecaRowMapper.mapear(rs);
    }

    @Override
    protected void definirId(Peca peca, long idGerado) {
        peca.setCodigoUnico(idGerado);
    }

    private void preencherCampos(PreparedStatement ps, Peca peca) throws SQLException {
        ps.setString(1, peca.getNome());
        ps.setString(2, peca.getCodigoBarras());
        ps.setString(3, peca.getFornecedorMarca());
        ps.setInt(4, peca.getQuantidadeEmEstoque());
        JdbcUtil.gravarDouble(ps, 5, peca.getPrecoCusto());
        JdbcUtil.gravarDouble(ps, 6, peca.getPrecoVenda());
        JdbcUtil.gravarDataHora(ps, 7, peca.getDataCadastro());
        JdbcUtil.gravarDataHora(ps, 8, peca.getDataUltimaAtualizacao());
        JdbcUtil.gravarEnum(ps, 9, peca.getCategoriaPeca());
        ps.setString(10, peca.getTamanho());
        ps.setString(11, peca.getCor());
    }
}