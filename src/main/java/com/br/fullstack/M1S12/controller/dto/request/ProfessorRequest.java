package com.br.fullstack.M1S12.controller.dto.request;

public record ProfessorRequest(String nome) {
    public ProfessorRequest {
        if (nome == null) {
            throw new IllegalArgumentException("O campo nome não pode ser nulo.");
        }
    }
}

