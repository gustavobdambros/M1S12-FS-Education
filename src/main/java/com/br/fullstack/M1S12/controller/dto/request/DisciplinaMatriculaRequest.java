package com.br.fullstack.M1S12.controller.dto.request;



public record DisciplinaMatriculaRequest(Long alunoId, Long disciplinaId, Double mediaFinal) {

    public DisciplinaMatriculaRequest {
        if (alunoId == null) {
            throw new IllegalArgumentException("O campo alunoId não pode ser nulo.");
        }
        if (disciplinaId == null) {
            throw new IllegalArgumentException("O campo disciplinaId não pode ser nulo.");
        }
        if (mediaFinal == null) {
            throw new IllegalArgumentException("O campo mediaFinal não pode ser nulo. Caso o aluno não possua uma média final definida, envie 0 no valor do campo.");
        }
    }
}
