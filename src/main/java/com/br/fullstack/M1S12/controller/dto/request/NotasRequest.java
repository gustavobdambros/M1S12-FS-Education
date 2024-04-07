package com.br.fullstack.M1S12.controller.dto.request;

import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import com.br.fullstack.M1S12.entity.ProfessorEntity;

public record NotasRequest(DisciplinaMatriculaEntity disciplina_matricula_id, Double nota, Double coeficiente) {
}
