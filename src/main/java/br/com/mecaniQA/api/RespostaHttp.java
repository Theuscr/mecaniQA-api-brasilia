package br.com.mecaniQA.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class RespostaHttp {

    private static final Logger LOGGER = Logger.getLogger(RespostaHttp.class.getName());

    private RespostaHttp() {
    }


    public static ResponseEntity<Void> naoEncontrado() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    public static ResponseEntity<ErroResponseDTO> requisicaoInvalida(DadosInvalidosException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponseDTO(e.getMessage()));
    }


    public static ResponseEntity<ErroResponseDTO> erroInterno(PersistenciaException e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponseDTO("Erro interno ao acessar o banco de dados."));
    }
}