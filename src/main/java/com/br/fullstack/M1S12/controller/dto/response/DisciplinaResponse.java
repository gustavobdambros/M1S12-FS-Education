package com.br.fullstack.M1S12.controller.dto.response;

import com.br.fullstack.M1S12.entity.ProfessorEntity;

public record DisciplinaResponse(Long id, String nome, ProfessorEntity professor) {
}
