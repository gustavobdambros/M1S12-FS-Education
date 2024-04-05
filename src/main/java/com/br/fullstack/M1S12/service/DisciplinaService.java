package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.controller.dto.request.DisciplinaRequest;
import com.br.fullstack.M1S12.controller.dto.response.DisciplinaResponse;
import com.br.fullstack.M1S12.entity.DisciplinaEntity;
import com.br.fullstack.M1S12.exception.NotFoundException;
import com.br.fullstack.M1S12.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public List<DisciplinaResponse> retornarDisciplinas() {
        List<DisciplinaEntity> listaDisciplinasEntity = disciplinaRepository.findAll();
        List<DisciplinaResponse> listaDisciplinasResponse = new ArrayList<>();

        if(listaDisciplinasEntity.isEmpty()) {
            throw new NotFoundException("Não há disciplinas no banco de dados.");
        }

        listaDisciplinasEntity.forEach(disciplinaEntity -> {
            DisciplinaResponse response = new DisciplinaResponse(
                    disciplinaEntity.getId(),
                    disciplinaEntity.getNome(),
                    disciplinaEntity.getProfessor());
            listaDisciplinasResponse.add(response);
        });

        return listaDisciplinasResponse;
    }

    public DisciplinaResponse buscarDisciplinaPorId(Long id) {
        DisciplinaEntity disciplinaEntity = disciplinaRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Disciplina com o id " + id + " não encontrada."));
        return new DisciplinaResponse(disciplinaEntity.getId(), disciplinaEntity.getNome(), disciplinaEntity.getProfessor());
    }

    public DisciplinaResponse salvarDisciplina(@RequestBody DisciplinaRequest disciplinaRequest) {
        DisciplinaEntity disciplinaEntity = new DisciplinaEntity();
        disciplinaEntity.setNome(disciplinaRequest.nome());
        disciplinaEntity.setProfessor(disciplinaRequest.professor());

        DisciplinaEntity disciplinaSalva = disciplinaRepository.save(disciplinaEntity);
        return new DisciplinaResponse(disciplinaSalva.getId(), disciplinaSalva.getNome(), disciplinaSalva.getProfessor());
    }

    public DisciplinaResponse atualizarDisciplina(Long id, DisciplinaRequest disciplinaRequest) {
        DisciplinaEntity disciplinaExistente = disciplinaRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Disciplina com o id " + id + " não encontrada."));

        disciplinaExistente.setNome(disciplinaRequest.nome());
        disciplinaExistente.setProfessor(disciplinaRequest.professor());

        DisciplinaEntity disciplinaAtualizada = disciplinaRepository.save(disciplinaExistente);
        return new DisciplinaResponse(disciplinaAtualizada.getId(), disciplinaAtualizada.getNome(), disciplinaAtualizada.getProfessor());
    }

    public void excluirDisciplina(Long id) {
        DisciplinaEntity disciplinaExistente = disciplinaRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Disciplina com o id " + id + " não encontrada."));
        disciplinaRepository.delete(disciplinaExistente);
    }
}
