package com.br.fullstack.M1S12.controller.dto.response;

import java.util.Date;

public record AlunoResponse(Long id,
                            String nome,
                            Date nascimento) {
}
