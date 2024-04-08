package com.br.fullstack.M1S12.controller.dto.request;

import java.util.Date;

public record AlunoRequest(String nome,
                           Date nascimento) {
    public AlunoRequest {
        if (nome == null) {
            throw new IllegalArgumentException("O campo nome não pode ser nulo.");
        }
    }
}
