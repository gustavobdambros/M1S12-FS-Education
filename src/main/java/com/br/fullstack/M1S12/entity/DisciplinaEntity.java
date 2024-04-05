package com.br.fullstack.M1S12.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "disciplinas")
@Data
@RequiredArgsConstructor
public class DisciplinaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @OneToMany
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professor;
}
