package com.br.fullstack.M1S12.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "disciplina_matriculas")
@Data
@Entity
public class DisciplinaMatriculaEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disciplina_matriculas_id_seq")
    @SequenceGenerator(name = "disciplina_matriculas_id_seq", sequenceName = "disciplina_matriculas_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private AlunoEntity aluno;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private DisciplinaEntity disciplina;

    @NotNull
    private LocalDate dataMatricula;

    @NotNull
    private Double mediaFinal;

    public DisciplinaMatriculaEntity(AlunoEntity aluno, DisciplinaEntity disciplina, LocalDate dataMatricula, Double mediaFinal) {
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.dataMatricula = dataMatricula;
        this.mediaFinal = mediaFinal;
    }
}
