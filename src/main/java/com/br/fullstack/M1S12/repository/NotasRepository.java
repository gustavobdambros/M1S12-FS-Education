package com.br.fullstack.M1S12.repository;


import com.br.fullstack.M1S12.entity.NotasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotasRepository extends JpaRepository<NotasEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM NotasEntity n WHERE n.disciplina_matricula_id.id = :matriculaId")
    boolean temNotaDefinida(@Param("matriculaId") Long matriculaId);

    @Query("SELECT n FROM NotasEntity n WHERE n.disciplina_matricula_id.id = :matriculaId")
    List<NotasEntity> buscarNotasPorMatriculaId(@Param("matriculaId") Long matriculaId);

    @Query("SELECT n.coeficiente FROM NotasEntity n WHERE n.disciplina_matricula_id.id = :matriculaId")
    List<Double> buscarCoeficientesPorMatriculaId(@Param("matriculaId") Long matriculaId);


}

