package com.br.fullstack.M1S12.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Table(name = "alunos")
@Entity
@Data
public class AlunoEntity {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alunos_id_seq")
    @SequenceGenerator(name = "alunos_id_seq", sequenceName = "alunos_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    String nome;

    @Column(nullable = false)
    Date nascimento;
}
