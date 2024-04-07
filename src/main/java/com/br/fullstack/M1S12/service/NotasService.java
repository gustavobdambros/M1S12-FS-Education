package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.controller.dto.request.NotasRequest;
import com.br.fullstack.M1S12.controller.dto.response.NotasResponse;
import com.br.fullstack.M1S12.entity.NotasEntity;
import com.br.fullstack.M1S12.entity.ProfessorEntity;
import com.br.fullstack.M1S12.repository.DisciplinaMatriculaRepository;
import com.br.fullstack.M1S12.repository.NotasRepository;
import com.br.fullstack.M1S12.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import com.br.fullstack.M1S12.exception.NotFoundException;
import org.springframework.http.ResponseEntity;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotasService {

    @Autowired
    private NotasRepository notasRepository;
    @Autowired
    private ProfessorRepository professorRepository;

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
        double novaMediaFinal = recalcularMediaFinalDelete(matricula, coeficiente, valorNota);

        log.info("Salvando matrícula atualizada no banco de dados...");
        matricula.setMediaFinal(novaMediaFinal);
        disciplinaMatriculaRepository.save(matricula);
        log.info("Matrícula atualizada salva!");

        notasRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private double recalcularMediaFinalDelete(DisciplinaMatriculaEntity matricula, double coeficienteExcluido, double valorNotaExcluida) {
        log.info("Recalculando média final...");
        double mediaFinalAtual = matricula.getMediaFinal();
        double somaNotasAtuais = mediaFinalAtual * (1 - coeficienteExcluido);
        double novaSomaNotas = somaNotasAtuais - (coeficienteExcluido * valorNotaExcluida);
        log.info("Cálculo da média final atualizado com sucesso.");
        return novaSomaNotas / (1 - coeficienteExcluido);
    }

    public NotasResponse salvarNota(NotasRequest notasRequest) {
        NotasEntity notasEntity = new NotasEntity();
        notasEntity.setDisciplina_matricula_id(notasRequest.disciplina_matricula_id());
        notasEntity.setNota(notasRequest.nota());
        notasEntity.setCoeficiente(notasRequest.coeficiente());

        //TODO: RECEBER PROFESSOR DA DISCIPLINA EM QUE A NOTA FOI LANÇADA

        //TODO: FAZER CÁLCULO AUTOMÁTICO DA MÉDIA DO ALUNO NA DISCIPLINA

        NotasEntity notaSalva = notasRepository.save(notasEntity);
        return new NotasResponse(notaSalva.getId(), notaSalva.getDisciplina_matricula_id(), notaSalva.getProfessor_id(), notaSalva.getNota(), notaSalva.getCoeficiente());

    }
}

