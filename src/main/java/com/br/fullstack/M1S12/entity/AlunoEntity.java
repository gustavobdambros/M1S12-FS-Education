package com.br.fullstack.M1S12.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Table(name = "alunos")
@Entity
@Data
public class AlunoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    String nome;

    @Column(nullable = false)
    Date nascimento;
}
