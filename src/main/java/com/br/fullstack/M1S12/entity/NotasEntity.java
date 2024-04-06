package com.br.fullstack.M1S12.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "notas")
@Entity
public class NotasEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notas_id_seq")
    @SequenceGenerator(name = "notas_id_seq", sequenceName = "notas_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "disciplina_matricula_id")
    private DisciplinaMatriculaEntity disciplina_matricula_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professor_id;

    @NotNull
    private Double nota;

    @NotNull
    private Double coeficiente;

}
