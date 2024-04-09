package com.br.fullstack.M1S12.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "disciplinas")
@Data
@RequiredArgsConstructor
public class DisciplinaEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "disciplinas_id_seq")
    @SequenceGenerator(name = "disciplinas_id_seq", sequenceName = "disciplinas_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne()
    @JoinColumn(name = "professores_id")
    private ProfessorEntity professor;
}
