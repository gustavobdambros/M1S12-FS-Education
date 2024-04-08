package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.controller.dto.request.NotasRequest;
import com.br.fullstack.M1S12.controller.dto.response.NotasResponse;
import com.br.fullstack.M1S12.entity.NotasEntity;
import com.br.fullstack.M1S12.repository.DisciplinaMatriculaRepository;
import com.br.fullstack.M1S12.repository.NotasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import com.br.fullstack.M1S12.exception.NotFoundException;
import org.springframework.http.ResponseEntity;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotasService {

    @Autowired
    private NotasRepository notasRepository;

    private final DisciplinaMatriculaRepository disciplinaMatriculaRepository;

    public List<NotasEntity> buscarNotasPorMatriculaId(Long matriculaId) {
        return notasRepository.buscarNotasPorMatriculaId(matriculaId);
    }

    public ResponseEntity<?> deletarNota(Long id) {
        log.info("Processo de deleção de nota iniciado. ID recebido {}", id);

        log.info("Tentativa de busca por nota em matrículas com o ID " + id + " para a exclusão...");
        DisciplinaMatriculaEntity matricula = notasRepository.findById(id).map(
                NotasEntity::getDisciplina_matricula_id).orElseThrow(
                () -> new NotFoundException("Nota não encontrada com o ID: " + id));
        log.info("Nota encontrada e com matrícula associada. ID Nota: " + id);

        log.info("Atualizando os dados para recalcular a média final...");
        double coeficiente = notasRepository.findById(id).get().getCoeficiente();
        double valorNota = notasRepository.findById(id).get().getNota();
        double novaMediaFinal = recalcularMediaFinal(matricula, coeficiente, valorNota,false);

        log.info("Salvando matrícula atualizada no banco de dados...");
        matricula.setMediaFinal(novaMediaFinal);
        disciplinaMatriculaRepository.save(matricula);
        log.info("Matrícula atualizada salva!");

        notasRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private double recalcularMediaFinal(DisciplinaMatriculaEntity matricula, double coeficiente, double valorNota, boolean adicionarNota) {
        log.info("Recalculando média final...");
        double mediaFinalAtual = matricula.getMediaFinal();
        double novaMediaFinal;

        if (adicionarNota) {
            novaMediaFinal = mediaFinalAtual + (valorNota * coeficiente);
        } else {
            novaMediaFinal = mediaFinalAtual - (valorNota * coeficiente);
        }

        log.info("Cálculo da média final atualizado com sucesso!");
        return novaMediaFinal;
    }

    public NotasResponse salvarNota(NotasRequest notasRequest) {
        log.info("Processo de salvamento de nota iniciado.");
        NotasEntity notasEntity = new NotasEntity();
        log.info("Nova entidade de notas criada");
        log.info("Validando existencia da matrícula.");
        DisciplinaMatriculaEntity matricula = validarExistenciaMatricula(notasRequest.disciplina_matricula_id());
        log.info("ID de matrícula recebida como atributo.");
        notasEntity.setDisciplina_matricula_id(matricula);
        notasEntity.setNota(notasRequest.nota());
        notasEntity.setCoeficiente(notasRequest.coeficiente());

        notasEntity.setProfessor_id(matricula.getDisciplina().getProfessor());

        log.info("Atributos recebidos na nova entidade de notas.");

        log.info("A média será recalculada com a nova nota adicionada ao sistema.");

        recalcularMediaFinal(notasEntity.getDisciplina_matricula_id(), notasEntity.getCoeficiente(), notasEntity.getNota(), true);

        log.info("Salvando nota no sistema...");
        NotasEntity notaSalva = notasRepository.save(notasEntity);
        log.info("Nota salva no sistema com sucesso.");
        return new NotasResponse(notaSalva.getId(), notaSalva.getDisciplina_matricula_id(), notaSalva.getProfessor_id(), notaSalva.getNota(), notaSalva.getCoeficiente());
    }

    private DisciplinaMatriculaEntity validarExistenciaMatricula(Long id) {
        DisciplinaMatriculaEntity matricula = notasRepository.findById(id).map(
                NotasEntity::getDisciplina_matricula_id).orElseThrow(
                () -> new NotFoundException("Nota não encontrada com o ID: " + id));
        return matricula;
    }
}