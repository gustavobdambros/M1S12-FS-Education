package com.br.fullstack.M1S12.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "professores")
@Data
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;
}
