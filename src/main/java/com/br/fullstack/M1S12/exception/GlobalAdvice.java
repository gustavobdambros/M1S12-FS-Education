package com.br.fullstack.M1S12.exception;

import com.br.fullstack.M1S12.exception.dto.Error;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception e) {
        Error erro = Error.builder()
                .codigo("500")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(500).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handler(DataIntegrityViolationException e) {
        Error erro = Error.builder()
                .codigo("400")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erro);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handler(NotFoundException e) {
        Error erro = Error.builder()
                .codigo("404")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(erro);
    }
}
