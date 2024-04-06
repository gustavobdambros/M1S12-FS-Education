package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.controller.dto.request.ProfessorRequest;
import com.br.fullstack.M1S12.controller.dto.response.ProfessorResponse;
import com.br.fullstack.M1S12.entity.ProfessorEntity;
import com.br.fullstack.M1S12.exception.NotFoundException;
import com.br.fullstack.M1S12.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public List<ProfessorResponse> retornarProfessores() {
        List<ProfessorEntity> listaProfessores = professorRepository.findAll();
        List<ProfessorResponse> listaProfessoresResponse = new ArrayList<>();

        if (listaProfessores.isEmpty()) {
            throw new NotFoundException("Não há professores no banco de dados.");
        }

        listaProfessores.forEach(professor -> {
            ProfessorResponse response = new ProfessorResponse(professor.getId(), professor.getNome());
            listaProfessoresResponse.add(response);
        });

        return listaProfessoresResponse;
    }

    public ProfessorResponse buscarProfessorPorId(Long id) {
        ProfessorEntity professor = professorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Professor com o id " + id + " não encontrado."));
        return new ProfessorResponse(professor.getId(), professor.getNome());
    }

    public ProfessorResponse salvarProfessor(@RequestBody ProfessorRequest professorRequest) {
        ProfessorEntity professorEntity = new ProfessorEntity();
        professorEntity.setNome(professorRequest.nome());

        ProfessorEntity professorSalvo = professorRepository.save(professorEntity);
        return new ProfessorResponse(professorSalvo.getId(), professorSalvo.getNome());
    }

    public ProfessorResponse atualizarProfessor(Long id, ProfessorRequest professorRequest) {
        ProfessorEntity professorExistente = professorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Professor com o id " + id + " não encontrado."));

        professorExistente.setNome(professorRequest.nome());

        ProfessorEntity professorAtualizado = professorRepository.save(professorExistente);
        return new ProfessorResponse(professorAtualizado.getId(), professorAtualizado.getNome());
    }

    public void excluirProfessor(Long id) {
        ProfessorEntity professorExistente = professorRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Professor com o id " + id + " não encontrado."));
        professorRepository.delete(professorExistente);
    }
}

