package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.entity.NotasEntity;
import com.br.fullstack.M1S12.repository.NotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotasService {

    @Autowired
    private NotasRepository notasRepository;

    public List<NotasEntity> buscarNotasPorMatriculaId(Long matriculaId) {
        return notasRepository.buscarNotasPorMatriculaId(matriculaId);
    }
}

