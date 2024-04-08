package com.br.fullstack.M1S12.controller.dto.request;

public record NotasRequest(Long disciplina_matricula_id, Double nota, Double coeficiente) {
    public NotasRequest {
        if (nota == null) {
            throw new IllegalArgumentException("O campo nota não pode ser nulo.");
        }
        if (coeficiente == null) {
            throw new IllegalArgumentException("O campo coeficiente não pode ser nulo.");
        }
        if (disciplina_matricula_id == null) {
            throw new IllegalArgumentException("O campo disciplina_matricula_id não pode ser nulo.");
        }
    }
}