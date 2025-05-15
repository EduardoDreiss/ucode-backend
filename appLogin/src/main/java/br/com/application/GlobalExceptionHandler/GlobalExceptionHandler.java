package br.com.application.GlobalExceptionHandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        System.err.println("Erro de integridade de dados: " + ex.getMessage());
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("mensagem", "O e-mail já está cadastrado."); // Mensagem mais específica
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // Outros tratadores de exceção podem ser adicionados aqui
}