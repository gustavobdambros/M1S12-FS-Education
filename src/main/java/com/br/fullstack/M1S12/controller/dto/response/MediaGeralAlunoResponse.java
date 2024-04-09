package com.br.fullstack.M1S12.controller.dto.response;

import java.util.List;
import java.util.Map;

public record MediaGeralAlunoResponse(Long alunoId, String nomeAluno, List<Map<String, Double>> mediaPorDisciplina,
                                      Double mediaFinal) {
}
