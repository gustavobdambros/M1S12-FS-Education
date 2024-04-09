package com.br.fullstack.M1S12.controller;

import com.br.fullstack.M1S12.controller.dto.request.ProfessorRequest;
import com.br.fullstack.M1S12.controller.dto.response.ProfessorResponse;
import com.br.fullstack.M1S12.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("professores")
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorResponse>> retornarProfessores() {
        log.info("Requisição para retornar todos os professores.");
        List<ProfessorResponse> professores = professorService.retornarProfessores();
        log.info("Busca por todos os professores concluída.");
        return ResponseEntity.ok(professores);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfessorResponse> buscarProfessorPorId(@PathVariable Long id) {
        log.info("Requisição para buscar professor por ID: {}", id);
        ProfessorResponse professorEncontrado = professorService.buscarProfessorPorId(id);
        log.info("Busca por professor com ID {} concluída", id);
        return ResponseEntity.ok(professorEncontrado);
    }

    @PostMapping
    public ResponseEntity<ProfessorResponse> salvarProfessor(@RequestBody ProfessorRequest professorRequest) {
        log.info("Requisição para salvar novo professor: {}", professorRequest);
        ProfessorResponse professorSalvo = professorService.salvarProfessor(professorRequest);
        log.info("Novo professor salvo com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(professorSalvo);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProfessorResponse> atualizarProfessor(@PathVariable Long id, @RequestBody ProfessorRequest professorRequest) {
        log.info("Requisição para atualizar professor com ID {}: {}", id, professorRequest);
        ProfessorResponse responseEntity = professorService.atualizarProfessor(id, professorRequest);
        log.info("Professor atualizado com sucesso. ID: {}", id);
        return ResponseEntity.ok(responseEntity);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluirProfessor(@PathVariable Long id) {
        log.info("Requisição para excluir professor com ID: {}", id);
        professorService.excluirProfessor(id);
        log.info("Professor excluído com sucesso. ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}

