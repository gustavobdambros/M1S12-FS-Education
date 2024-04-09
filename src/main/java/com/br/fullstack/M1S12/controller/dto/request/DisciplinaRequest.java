package com.br.fullstack.M1S12.controller.dto.request;

public record DisciplinaRequest(String nome, Long professorId) {
    public DisciplinaRequest {
        if (nome == null) {
            throw new IllegalArgumentException("O campo nome não pode ser nulo.");
        }
        if (professorId == null) {
            throw new IllegalArgumentException("O campo professorId não pode ser nulo.");
        }
    }
}
