package com.br.fullstack.M1S12.controller.dto.response;

import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import com.br.fullstack.M1S12.entity.ProfessorEntity;

public record NotasResponse(Long id, DisciplinaMatriculaEntity disciplina_matricula_id, ProfessorEntity professor_id, Double nota, Double coeficiente) {
}
