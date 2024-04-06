package com.br.fullstack.M1S12.controller.dto.request;

import com.br.fullstack.M1S12.entity.ProfessorEntity;

public record DisciplinaRequest(String nome, ProfessorEntity professor) {
}
