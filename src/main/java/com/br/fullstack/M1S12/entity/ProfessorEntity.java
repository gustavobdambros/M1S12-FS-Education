package com.br.fullstack.M1S12.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "professores")
@Data
public class ProfessorEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professores_id_seq")
    @SequenceGenerator(name = "professores_id_seq", sequenceName = "professores_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String nome;
}
