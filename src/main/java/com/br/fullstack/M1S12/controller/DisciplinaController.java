package com.br.fullstack.M1S12.controller;

import com.br.fullstack.M1S12.controller.dto.request.DisciplinaRequest;
import com.br.fullstack.M1S12.controller.dto.response.DisciplinaResponse;
import com.br.fullstack.M1S12.entity.DisciplinaEntity;
import com.br.fullstack.M1S12.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("disciplinas")
@RequiredArgsConstructor
@Slf4j
public class DisciplinaController {

    //INJEÇÃO DE DEPENDÊNCIAS
    private final DisciplinaService disciplinaService;
    private static Logger logger = LoggerFactory.getLogger(DisciplinaController.class);

    //CRUD
    @GetMapping
    public ResponseEntity<List<DisciplinaResponse>> retornarDisciplinas() {
        log.info("Requisição para retornar todas as disciplinas.");
        List<DisciplinaResponse> disciplinas = disciplinaService.retornarDisciplinas();
        log.info("Busca por todas as disciplinas concluída.");
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("{id}")
    public ResponseEntity<DisciplinaResponse> buscarDisciplinaPorId(@PathVariable Long id) {
        log.info("Requisição para buscar aluno por ID: {}", id);
        DisciplinaResponse disciplinaEncontrada = disciplinaService.buscarDisciplinaPorId(id);
        log.info("Busca por aluno com ID {} concluída", id);
        return ResponseEntity.ok(disciplinaEncontrada);
    }

    @PostMapping
    public ResponseEntity<DisciplinaResponse> salvarDisciplina(@RequestBody DisciplinaRequest disciplinaRequest) {
        log.info("Requisição para salvar nova disciplina: {}", disciplinaRequest);
        DisciplinaResponse disciplinaSalva = disciplinaService.salvarDisciplina(disciplinaRequest);
        log.info("Nova disciplina salva com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaSalva);
    }

    @PutMapping("{id}")
    public ResponseEntity<DisciplinaResponse> atualizarDisciplina(@PathVariable Long id, @RequestBody DisciplinaRequest disciplinaRequest) {
        log.info("Requisição para atualizar disciplina com ID {}: {}", id, disciplinaRequest);
        ResponseEntity<DisciplinaResponse> responseEntity = ResponseEntity.ok(disciplinaService.atualizarDisciplina(id, disciplinaRequest));
        log.info("Disciplina atualizada com sucesso. ID: {}", id);
        return responseEntity;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluirDisciplina(@PathVariable Long id) {
        log.info("Requisição para excluir disciplina com ID: {}", id);
        disciplinaService.excluirDisciplina(id);
        log.info("Disciplina excluída com sucesso. ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
