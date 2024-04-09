package com.br.fullstack.M1S12.controller;

import com.br.fullstack.M1S12.controller.dto.request.AlunoRequest;
import com.br.fullstack.M1S12.controller.dto.response.AlunoResponse;
import com.br.fullstack.M1S12.service.AlunoService;
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
@RequestMapping("alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoResponse>> retornarAlunos() {
        log.info("Requisição para retornar todos os alunos.");
        List<AlunoResponse> alunos = alunoService.retornarAlunos();
        log.info("Busca por todos os alunos concluída.");
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("{id}")
    public ResponseEntity<AlunoResponse> buscarAlunoPorId(@PathVariable Long id) {
        log.info("Requisição para buscar aluno por ID: {}", id);
        AlunoResponse alunoEncontrado = alunoService.buscarAlunoPorId(id);
        log.info("Busca por aluno com ID {} concluída", id);
        return ResponseEntity.ok(alunoEncontrado);
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> salvarAluno(@RequestBody AlunoRequest alunoRequest) {
        log.info("Requisição para salvar novo aluno: {}", alunoRequest);
        AlunoResponse alunoSalvo = alunoService.salvarAluno(alunoRequest);
        log.info("Novo aluno salvo com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
    }

    @PutMapping("{id}")
    public ResponseEntity<AlunoResponse> atualizarAluno(@PathVariable Long id, @RequestBody AlunoRequest alunoRequest) {
        log.info("Requisição para atualizar aluno com ID {}: {}", id, alunoRequest);
        ResponseEntity<AlunoResponse> responseEntity = ResponseEntity.ok(alunoService.atualizarAluno(id, alunoRequest));
        log.info("Aluno atualizado com sucesso. ID: {}", id);
        return responseEntity;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluirAluno(@PathVariable Long id) {
        log.info("Requisição para excluir aluno com ID: {}", id);
        alunoService.excluirAluno(id);
        log.info("Aluno excluído com sucesso. ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
