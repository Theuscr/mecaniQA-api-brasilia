package br.com.mecaniQA.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

public final class JdbcUtil {

    private JdbcUtil() {
    }



    public static Double lerDouble(ResultSet rs, String coluna) throws SQLException {
        double valor = rs.getDouble(coluna);
        return rs.wasNull() ? null : valor;
    }

    public static LocalDateTime lerDataHora(ResultSet rs, String coluna) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(coluna);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    public static <E extends Enum<E>> E lerEnum(ResultSet rs, String coluna, Class<E> tipo) throws SQLException {
        String texto = rs.getString(coluna);
        return texto == null ? null : Enum.valueOf(tipo, texto);
    }



    public static void gravarDouble(PreparedStatement ps, int indice, Double valor) throws SQLException {
        if (valor == null) {
            ps.setNull(indice, Types.DECIMAL);
        } else {
            ps.setDouble(indice, valor);
        }
    }

    public static void gravarDataHora(PreparedStatement ps, int indice, LocalDateTime valor) throws SQLException {
        if (valor == null) {
            ps.setNull(indice, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(indice, Timestamp.valueOf(valor));
        }
    }

    public static void gravarEnum(PreparedStatement ps, int indice, Enum<?> valor) throws SQLException {
        if (valor == null) {
            ps.setNull(indice, Types.VARCHAR);
        } else {
            ps.setString(indice, valor.name());
        }
    }
}