package com.br.fullstack.M1S12.controller;

import com.br.fullstack.M1S12.entity.NotasEntity;
import com.br.fullstack.M1S12.service.NotasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotasController {

    @Autowired
    private NotasService notasService;

    private static final Logger logger = LoggerFactory.getLogger(NotasController.class);

    @GetMapping("/matricula/{matriculaId}")
    public ResponseEntity<List<NotasEntity>> buscarNotasPorMatricula(@PathVariable Long matriculaId) {
        logger.info("Buscando notas para a matrícula ID: {}", matriculaId);
        List<NotasEntity> notas = notasService.buscarNotasPorMatriculaId(matriculaId);
        if (notas.isEmpty()) {
            logger.info("Nenhuma nota encontrada para a matrícula ID: {}", matriculaId);
            return ResponseEntity.notFound().build();
        }
        logger.info("Notas encontradas para a matrícula ID: {}", matriculaId);
        return ResponseEntity.ok(notas);
    }
}
