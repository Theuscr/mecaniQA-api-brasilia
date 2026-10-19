package br.com.mecaniQA.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class AbstractJdbcRepository<T, ID> implements Repository<T, ID> {


    @FunctionalInterface
    protected interface OperacaoJdbc<R> {
        R executar(Connection conexao) throws SQLException;
    }



    protected abstract String sqlInsert();

    protected abstract String sqlUpdate();

    protected abstract String sqlSelectById();

    protected abstract String sqlSelectAll();

    protected abstract String sqlDeleteById();

    protected abstract String sqlExistsById();


    protected abstract void preencherInsert(PreparedStatement ps, T entidade) throws SQLException;


    protected abstract void preencherUpdate(PreparedStatement ps, T entidade) throws SQLException;


    protected abstract T mapear(ResultSet rs) throws SQLException;


    protected abstract void definirId(T entidade, long idGerado);




    protected void aposInserir(Connection conexao, T entidade) throws SQLException {
    }


    protected void aposCarregar(Connection conexao, T entidade) throws SQLException {
    }



    @Override
    public final T save(T entity) {
        return executarEmTransacao(conexao -> {
            try (PreparedStatement ps = conexao.prepareStatement(sqlInsert(), Statement.RETURN_GENERATED_KEYS)) {
                preencherInsert(ps, entity);
                ps.executeUpdate();
                try (ResultSet chaves = ps.getGeneratedKeys()) {
                    if (chaves.next()) {
                        definirId(entity, chaves.getLong(1));
                    }
                }
            }
            aposInserir(conexao, entity);
            return entity;
        });
    }

    @Override
    public final Optional<T> findById(ID id) {
        return executarComConexao(conexao -> {
            T entidade = null;
            try (PreparedStatement ps = conexao.prepareStatement(sqlSelectById())) {
                ps.setObject(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        entidade = mapear(rs);
                    }
                }
            }
            if (entidade == null) {
                return Optional.<T>empty();
            }
            aposCarregar(conexao, entidade);
            return Optional.of(entidade);
        });
    }

    @Override
    public final List<T> findAll() {
        return executarComConexao(conexao -> {
            List<T> entidades = new ArrayList<>();
            try (PreparedStatement ps = conexao.prepareStatement(sqlSelectAll());
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entidades.add(mapear(rs));
                }
            }
            for (T entidade : entidades) {
                aposCarregar(conexao, entidade);
            }
            return entidades;
        });
    }

    @Override
    public final T update(T entity) {
        return executarEmTransacao(conexao -> {
            try (PreparedStatement ps = conexao.prepareStatement(sqlUpdate())) {
                preencherUpdate(ps, entity);
                ps.executeUpdate();
            }
            return entity;
        });
    }

    @Override
    public final void deleteById(ID id) {
        executarEmTransacao(conexao -> {
            try (PreparedStatement ps = conexao.prepareStatement(sqlDeleteById())) {
                ps.setObject(1, id);
                ps.executeUpdate();
            }
            return null;
        });
    }

    @Override
    public final boolean existsById(ID id) {
        return executarComConexao(conexao -> {
            try (PreparedStatement ps = conexao.prepareStatement(sqlExistsById())) {
                ps.setObject(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        });
    }


    protected final <R> R executarComConexao(OperacaoJdbc<R> operacao) {
        try (Connection conexao = ConexaoBanco.obterConexao()) {
            return operacao.executar(conexao);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DadosInvalidosException(
                    "A operação viola uma restrição de integridade do banco (registro em uso ou dado duplicado).");
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao executar a operação no banco de dados: " + e.getMessage(), e);
        }
    }

    /** Igual a executarComConexao, mas em transação: commit se deu certo, rollback se falhou. */
    protected final <R> R executarEmTransacao(OperacaoJdbc<R> operacao) {
        return executarComConexao(conexao -> {
            conexao.setAutoCommit(false);
            try {
                R resultado = operacao.executar(conexao);
                conexao.commit();
                return resultado;
            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }
        });
    }
}