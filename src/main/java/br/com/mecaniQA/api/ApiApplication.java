package br.com.mecaniQA.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		try {
			ConexaoBanco.validarConexao();
		} catch (ConexaoBancoException e) {
			System.err.println("[MecaniQA] ERRO DE CONEXÃO COM O BANCO DE DADOS");
			System.err.println("[MecaniQA] " + e.getMessage());
			System.exit(1);
		}
		SpringApplication.run(ApiApplication.class, args);
	}

}