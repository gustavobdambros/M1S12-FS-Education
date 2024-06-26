package com.br.fullstack.M1S12.exception;

import com.br.fullstack.M1S12.exception.dto.Error;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<List<Error>> handleNoSuchElementException(NoSuchElementException ex) {
        List<Error> errors = new ArrayList<>();

        String codigo = String.valueOf(HttpStatus.NOT_FOUND.value());
        String errorMessage = ex.getMessage();
        errors.add(new Error(codigo, errorMessage));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<List<Error>> handleIllegalStateException(IllegalStateException ex) {
        List<Error> errorResponses = new ArrayList<>();

        String codigo = String.valueOf(HttpStatus.CONFLICT.value());
        String errorMessage = ex.getMessage();

        errorResponses.add(new Error(codigo, errorMessage));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponses);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<Error>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        List<Error> errorResponses = new ArrayList<>();

        String codigo = String.valueOf(HttpStatus.BAD_REQUEST.value());
        String errorMessage = ex.getMessage();

        if (errorMessage.contains("Required request body is missing")) {
            errorMessage = ("O corpo da solicitação é obrigatório e está ausente.");
        }

        if (ex.getMostSpecificCause() instanceof IllegalArgumentException exception) {
            errorMessage = exception.getMessage();
        }


        errorResponses.add(new Error(codigo, errorMessage));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
    }

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
