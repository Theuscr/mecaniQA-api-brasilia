package br.com.mecaniQA.api;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        System.out.println("=== TESTE 1: abrir conexão com o MySQL ===");
        try (Connection conexao = ConexaoBanco.obterConexao()) {
            DatabaseMetaData meta = conexao.getMetaData();
            System.out.println("OK  URL:     " + meta.getURL());
            System.out.println("OK  Usuário: " + meta.getUserName());
            System.out.println("OK  Banco:   " + conexao.getCatalog());

            System.out.println();
            System.out.println("=== TESTE 2: as 7 tabelas existem? ===");
            String[] tabelas = {
                    "peca", "servico", "ordem_servico", "ordem_servico_servico",
                    "ordem_servico_peca", "pedido_peca", "item_pedido_peca"
            };
            int faltando = 0;
            for (String tabela : tabelas) {
                try (ResultSet rs = meta.getTables(conexao.getCatalog(), null, tabela, new String[]{"TABLE"})) {
                    if (rs.next()) {
                        System.out.println("OK  tabela " + tabela);
                    } else {
                        System.out.println("FALHA  tabela " + tabela + " NÃO existe (rode o script_mecaniqa.sql)");
                        faltando++;
                    }
                }
            }

            System.out.println();
            System.out.println("=== TESTE 3: consulta SQL manual (SELECT COUNT) ===");
            try (PreparedStatement ps = conexao.prepareStatement("SELECT COUNT(*) FROM peca");
                 ResultSet rs = ps.executeQuery()) {
                rs.next();
                System.out.println("OK  linhas na tabela peca: " + rs.getInt(1));
            }

            System.out.println();
            if (faltando == 0) {
                System.out.println("RESULTADO: TUDO CERTO");
            } else {
                System.out.println("RESULTADO: faltam " + faltando + " tabela(s)");
            }
        } catch (ConexaoBancoException e) {
            System.out.println("FALHA  " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("FALHA SQL  " + e.getMessage());
        }
    }
}