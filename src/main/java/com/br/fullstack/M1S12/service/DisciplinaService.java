package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.controller.dto.request.DisciplinaRequest;
import com.br.fullstack.M1S12.controller.dto.response.DisciplinaResponse;
import com.br.fullstack.M1S12.entity.DisciplinaEntity;
import com.br.fullstack.M1S12.entity.ProfessorEntity;
import com.br.fullstack.M1S12.exception.NotFoundException;
import com.br.fullstack.M1S12.repository.DisciplinaRepository;
import com.br.fullstack.M1S12.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

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
        Optional<ProfessorEntity> professor = professorRepository.findById(disciplinaRequest.professorId());
        disciplinaEntity.setProfessor(professor.get());

        DisciplinaEntity disciplinaSalva = disciplinaRepository.save(disciplinaEntity);
        return new DisciplinaResponse(disciplinaSalva.getId(), disciplinaSalva.getNome(), disciplinaSalva.getProfessor());
    }

    public DisciplinaResponse atualizarDisciplina(Long id, DisciplinaRequest disciplinaRequest) {
        DisciplinaEntity disciplinaExistente = disciplinaRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Disciplina com o id " + id + " não encontrada."));

        disciplinaExistente.setNome(disciplinaRequest.nome());
        Optional<ProfessorEntity> professor = professorRepository.findById(disciplinaRequest.professorId());
        disciplinaExistente.setProfessor(professor.get());

        DisciplinaEntity disciplinaAtualizada = disciplinaRepository.save(disciplinaExistente);
        return new DisciplinaResponse(disciplinaAtualizada.getId(), disciplinaAtualizada.getNome(), disciplinaAtualizada.getProfessor());
    }

    public void excluirDisciplina(Long id) {
        DisciplinaEntity disciplinaExistente = disciplinaRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Disciplina com o id " + id + " não encontrada."));
        disciplinaRepository.delete(disciplinaExistente);
    }
}
