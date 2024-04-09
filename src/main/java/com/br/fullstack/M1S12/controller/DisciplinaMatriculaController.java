package com.br.fullstack.M1S12.controller;


import com.br.fullstack.M1S12.controller.dto.request.DisciplinaMatriculaRequest;
import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import com.br.fullstack.M1S12.service.DisciplinaMatriculaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matricula")
@Slf4j
public class DisciplinaMatriculaController {

    private static final Logger logger = LoggerFactory.getLogger(DisciplinaMatriculaController.class);
    @Autowired
    private DisciplinaMatriculaService disciplinaMatriculaService;

    @PostMapping
    public DisciplinaMatriculaEntity criarDisciplinaMatricula(@RequestBody @Valid DisciplinaMatriculaRequest disciplinaMatricula) {
        logger.info("Recebendo chamada POST no endpoint /matricula. Dados recebidos: " + disciplinaMatricula.toString());
        DisciplinaMatriculaEntity disciplinaMatriculaReturn = disciplinaMatriculaService.criarDisciplinaMatricula(disciplinaMatricula);
        logger.info("Finalizando chamada POST no endpoint /matricula. Dados retornados: " + disciplinaMatriculaReturn.toString());
        return disciplinaMatriculaReturn;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        logger.info("Recebida chamada DELETE no endpoint /matricula/{id}. ID recebido: " + id);
        ResponseEntity<?> responseEntity = disciplinaMatriculaService.deletarDisciplinaMatricula(id);
        logger.info("Finalizada chamada DELETE no endpoint /matricula/{id}. Entidade com ID " + id + " exclida com sucesso.");
        return responseEntity;
    }

    @GetMapping("/{matriculaId}")
    public ResponseEntity<?> buscarDisciplinaMatriculaPorId(@PathVariable Long matriculaId) {
        logger.info("Recebendo chamada GET no endpoint /matricula/{matriculaId}. Dados recebidos: " + matriculaId);
        ResponseEntity<?> responseEntity = disciplinaMatriculaService.buscarDisciplinaMatriculaPorId(matriculaId);
        logger.info("Finalizada chamada GET no endpoint /matricula/{matriculaId}. Retornando dados: {}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/alunoId/{alunoId}")
    public List<DisciplinaMatriculaEntity> buscarTodasDisciplinaMatriculasPorAlunoId(@PathVariable Long alunoId) {
        logger.info("Recebendo chamada GET no endpoint /matricula/alunoId/{alunoId}. Dados recebidos: " + alunoId);
        List<DisciplinaMatriculaEntity> listaMatriculasAluno = disciplinaMatriculaService.buscarTodasDisciplinaMatriculasPorAlunoId(alunoId);
        logger.info("Finalizada chamada GET no endpoint /matricula/alunoId/{alunoId}. Retornando dados: {}", listaMatriculasAluno);
        return listaMatriculasAluno;
    }

    @GetMapping("/disciplinaId/{disciplinaId}")
    public List<DisciplinaMatriculaEntity> buscarTodasDisciplinaMatriculasPorDisciplinaId(@PathVariable Long disciplinaId) {
        logger.info("Recebendo chamada GET no endpoint /matricula/disciplinaId/{disciplinaId}. Dados recebidos: " + disciplinaId);
        List<DisciplinaMatriculaEntity> listaMatriculasDisciplinas = disciplinaMatriculaService.buscarTodasDisciplinaMatriculasPorDisciplinaId(disciplinaId);
        logger.info("Finalizada chamada GET no endpoint /matricula/disciplinaId/{disciplinaId}. Retornando dados: {}", listaMatriculasDisciplinas);
        return listaMatriculasDisciplinas;
    }

    @GetMapping("/alunoId/{alunoId}/mediaGeral")
    public ResponseEntity<?> calculaMediaFinalPorAlunoId(@PathVariable Long alunoId) {
        logger.info("Recebendo chamada GET no endpoint /matricula/alunoId/mediaFinal/{alunoId}. Dados recebidos: " + alunoId);
        ResponseEntity<?> mediaFinalPorAlunoId = disciplinaMatriculaService.recuperaMediasDisciplinasJuntoComMediaGeralPorAlunoId(alunoId);
        logger.info("Finalizada chamada GET no endpoint /matricula/alunoId/mediaFinal/{alunoId}. Retornando dados: {}", mediaFinalPorAlunoId);
        return mediaFinalPorAlunoId;
    }
}
