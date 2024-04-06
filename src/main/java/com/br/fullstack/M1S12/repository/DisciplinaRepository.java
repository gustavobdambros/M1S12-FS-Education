package com.br.fullstack.M1S12.repository;

import com.br.fullstack.M1S12.entity.DisciplinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<DisciplinaEntity, Long> {
}
