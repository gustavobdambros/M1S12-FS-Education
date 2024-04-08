package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.controller.dto.request.AlunoRequest;
import com.br.fullstack.M1S12.controller.dto.response.AlunoResponse;
import com.br.fullstack.M1S12.entity.AlunoEntity;
import com.br.fullstack.M1S12.exception.NotFoundException;
import com.br.fullstack.M1S12.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public List<AlunoResponse> retornarAlunos(){
        List<AlunoEntity> alunosEntity = alunoRepository.findAll();
        List<AlunoResponse> alunosResponse = new ArrayList<>();

        if(alunosEntity.isEmpty()) {
            throw new NotFoundException("Não há alunos no banco de dados!");
        }

        alunosEntity.forEach(alunoEntity -> {
            AlunoResponse response = new AlunoResponse(
                    alunoEntity.getId(),
                    alunoEntity.getNome(),
                    alunoEntity.getNascimento());
            alunosResponse.add(response);
        });

        return alunosResponse;
    }

    public AlunoResponse buscarAlunoPorId(Long id){

        AlunoEntity alunoEntity = alunoRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Aluno com o id " + id + " não encontrado!"));

        return new AlunoResponse(alunoEntity.getId(), alunoEntity.getNome(), alunoEntity.getNascimento());
    }

    public AlunoResponse salvarAluno(@RequestBody AlunoRequest alunoRequest){
        AlunoEntity alunoEntity = new AlunoEntity();
        alunoEntity.setNome(alunoRequest.nome());
        if(alunoRequest.nascimento() != null)
            alunoEntity.setNascimento(alunoRequest.nascimento());

        AlunoEntity alunoSalvo = alunoRepository.save(alunoEntity);
        return new AlunoResponse(alunoSalvo.getId(), alunoSalvo.getNome(), alunoSalvo.getNascimento());
    }

    public AlunoResponse atualizarAluno(Long id, AlunoRequest alunoRequest){
        AlunoEntity alunoExistente = alunoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Aluno com o id " + id + " não encontrado!"));

        if(alunoRequest.nome() != null)
            alunoExistente.setNome(alunoRequest.nome());
        if(alunoRequest.nascimento() != null)
            alunoExistente.setNascimento(alunoRequest.nascimento());

        AlunoEntity alunoAtualizado = alunoRepository.save(alunoExistente);

        return new AlunoResponse(alunoAtualizado.getId(), alunoAtualizado.getNome(),alunoAtualizado.getNascimento());
    }

    public void excluirAluno(Long id){
        AlunoEntity alunoExistente = alunoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Aluno com o id " + id + " não encontrado!"));

        alunoRepository.delete(alunoExistente);
    }
}
