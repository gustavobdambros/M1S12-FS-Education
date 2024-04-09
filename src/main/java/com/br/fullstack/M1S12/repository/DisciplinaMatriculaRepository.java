package com.br.fullstack.M1S12.repository;


import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaMatriculaRepository extends JpaRepository<DisciplinaMatriculaEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(dm) > 0 THEN true ELSE false END FROM DisciplinaMatriculaEntity dm WHERE dm.aluno.id = :alunoId AND dm.disciplina.id = :disciplinaId")
    boolean matriculaExisteParaAlunoNaDisciplina(@Param("alunoId") Long alunoId, @Param("disciplinaId") Long disciplinaId);

    @Query("SELECT dm FROM DisciplinaMatriculaEntity dm WHERE dm.aluno.id = :alunoId")
    List<DisciplinaMatriculaEntity> buscaTodasPorAlunoId(@Param("alunoId") Long alunoId);

    @Query("SELECT dm FROM DisciplinaMatriculaEntity dm WHERE dm.disciplina.id = :disciplinaId")
    List<DisciplinaMatriculaEntity> buscaTodasPorDisciplinaId(@Param("disciplinaId") Long disciplinaId);

    @Transactional
    @Modifying
    @Query("UPDATE DisciplinaMatriculaEntity dm SET dm.mediaFinal = :mediaFinal WHERE dm.id = :matriculaId")
    void atualizaMediaFinalPorMatriculaId(@Param("matriculaId") Long matriculaId, @Param("mediaFinal") Double mediaFinal);
}
