package br.com.mecaniQA.api;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConexaoBanco {

    private static final String URL_PADRAO = "jdbc:mysql://localhost:3306/mecaniqa";
    private static final String USUARIO_PADRAO = "root";
    private static final String SENHA_PADRAO = "";

    private static final String url;
    private static final String usuario;
    private static final String senha;

    static {
        Properties propriedades = carregarPropriedades();
        url = escolher("DB_URL", propriedades.getProperty("db.url"), URL_PADRAO);
        usuario = escolher("DB_USER", propriedades.getProperty("db.user"), USUARIO_PADRAO);
        senha = escolher("DB_PASSWORD", propriedades.getProperty("db.password"), SENHA_PADRAO);
    }

    private ConexaoBanco() {
    }

    public static Connection obterConexao() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new ConexaoBancoException(
                    "Não foi possível conectar ao MySQL em " + url + " com o usuário '" + usuario
                            + "'. Verifique se o servidor está no ar, se o banco 'mecaniqa' foi criado "
                            + "(script_mecaniqa.sql) e se usuário e senha estão corretos. Detalhe: "
                            + e.getMessage(), e);
        }
    }

    public static void validarConexao() {
        try (Connection conexao = obterConexao()) {
            conexao.isValid(2);
        } catch (SQLException e) {
            throw new ConexaoBancoException("Falha ao validar a conexão com o MySQL: " + e.getMessage(), e);
        }
    }

    private static Properties carregarPropriedades() {
        Properties propriedades = new Properties();
        try (InputStream entrada = ConexaoBanco.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (entrada != null) {
                propriedades.load(entrada);
            }
        } catch (IOException e) {
            // Sem o arquivo, seguem as variáveis de ambiente ou os valores padrão.
        }
        return propriedades;
    }

    private static String escolher(String variavelAmbiente, String valorArquivo, String valorPadrao) {
        String valorAmbiente = System.getenv(variavelAmbiente);
        if (valorAmbiente != null) {
            return valorAmbiente;
        }
        if (valorArquivo != null) {
            return valorArquivo.trim();
        }
        return valorPadrao;
    }
}