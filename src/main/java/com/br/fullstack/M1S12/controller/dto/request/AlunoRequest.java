package com.br.fullstack.M1S12.controller.dto.request;

import java.util.Date;

public record AlunoRequest(String nome,
                           Date nascimento) {
}
