package com.br.fullstack.M1S12.controller;

import com.br.fullstack.M1S12.controller.dto.request.NotasRequest;
import com.br.fullstack.M1S12.controller.dto.response.NotasResponse;
import com.br.fullstack.M1S12.entity.NotasEntity;
import com.br.fullstack.M1S12.service.NotasService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@Slf4j
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

    @PostMapping
    public ResponseEntity<NotasResponse> salvarNota(@RequestBody NotasRequest notasRequest) {
        log.info("Requisição para salvar nova nota: {}", notasRequest);
        NotasResponse notaSalva = notasService.salvarNota(notasRequest);
        log.info("Nova nota criada com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(notaSalva);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarNota(@PathVariable Long id){
        log.info("Chamada para DELETE no endpoint /nota/{id} recebida. ID: " + id);
        ResponseEntity<?> responseEntity = notasService.deletarNota(id);
        log.info("Finalizada chamada DELETE no endpoint /nota/{id}. Nota com o id " + id + "deletada com sucesso.");
        return responseEntity;
    }
}
