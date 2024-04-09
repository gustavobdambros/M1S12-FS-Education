package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.controller.dto.request.NotasRequest;
import com.br.fullstack.M1S12.controller.dto.response.NotasResponse;
import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import com.br.fullstack.M1S12.entity.NotasEntity;
import com.br.fullstack.M1S12.exception.NotFoundException;
import com.br.fullstack.M1S12.repository.DisciplinaMatriculaRepository;
import com.br.fullstack.M1S12.repository.NotasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotasService {

    private final DisciplinaMatriculaRepository disciplinaMatriculaRepository;
    @Autowired
    private NotasRepository notasRepository;

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

        notasRepository.deleteById(id);

        atualizaMediaFinalPorIdMatricula(matricula.getId());

        return ResponseEntity.noContent().build();
    }

    private void atualizaMediaFinalPorIdMatricula(Long matriculaId) {
        List<NotasEntity> notasList = recuperaNotasPorIdMatricula(matriculaId);

        double somaNotasMultiplicadas = 0;

        log.info("Iniciado calculo da media com as notas recuperadas.");
        for (NotasEntity nota : notasList) {
            double notaComCoeficiente = nota.getNota() * nota.getCoeficiente();
            log.info("Calculo da nota pelo coeficiente: {} * {} = {}", nota.getNota(), nota.getCoeficiente(), notaComCoeficiente);

            somaNotasMultiplicadas += notaComCoeficiente;
            log.info("Soma das notas para definição das medias: {} + {}", somaNotasMultiplicadas, notaComCoeficiente);
        }

        disciplinaMatriculaRepository.atualizaMediaFinalPorMatriculaId(matriculaId, somaNotasMultiplicadas);

    }

    private List<NotasEntity> recuperaNotasPorIdMatricula(Long matriculaId) {
        log.info("Buscando todas as notas para matricula de ID: {}", matriculaId);
        List<NotasEntity> notasEntityList = notasRepository.buscarNotasPorMatriculaId(matriculaId);
        log.info("Notas recuperadas: {}", notasEntityList.toString());
        return notasEntityList;
    }

    private List<Double> recuperaCoeficientesPorIdMatricula(Long matriculaId) {
        log.info("Buscando todas as notas para matricula de ID: {}", matriculaId);
        List<Double> notasEntityList = notasRepository.buscarCoeficientesPorMatriculaId(matriculaId);
        log.info("Notas recuperadas: {}", notasEntityList.toString());
        return notasEntityList;
    }

    public NotasResponse salvarNota(NotasRequest notasRequest) {
        log.info("Processo de salvamento de nota iniciado.");
        NotasEntity notasEntity = new NotasEntity();
        log.info("Nova entidade de notas criada");
        log.info("Validando existencia da matrícula.");
        DisciplinaMatriculaEntity matricula = validarExistenciaMatricula(notasRequest.disciplina_matricula_id());
        log.info("ID de matrícula recebida como atributo.");
        notasEntity.setDisciplina_matricula_id(matricula);

        validarNotaCoeficiente(notasRequest.disciplina_matricula_id(), notasRequest.coeficiente(), notasRequest.nota());
        notasEntity.setNota(notasRequest.nota());
        notasEntity.setCoeficiente(notasRequest.coeficiente());

        notasEntity.setProfessor_id(matricula.getDisciplina().getProfessor());

        log.info("Atributos recebidos na nova entidade de notas.");

        log.info("Salvando nota no sistema...");
        NotasEntity notaSalva = notasRepository.save(notasEntity);
        log.info("Nota salva no sistema com sucesso.");

        atualizaMediaFinalPorIdMatricula(notasRequest.disciplina_matricula_id());

        return new NotasResponse(notaSalva.getId(), notasRequest.disciplina_matricula_id(), notaSalva.getNota(), notaSalva.getCoeficiente());
    }

    private void validarNotaCoeficiente(Long matriculaId, Double coeficienteNovo, Double notaNova) {
        List<Double> coeficienteList = recuperaCoeficientesPorIdMatricula(matriculaId);
        double soma = 0.0;
        double somaComCoeficienteNovo;

        for (Double coeficiente : coeficienteList) {
            soma += coeficiente;
        }

        somaComCoeficienteNovo = soma + coeficienteNovo;

        if (somaComCoeficienteNovo > 1) {
            throw new IllegalStateException("O coeficiente " + coeficienteNovo + " não é valido. A somatoria total dos coeficientes deve ser igual a 1. Valor dos coeficientes cadastrados até o momento: " + soma);
        }

        if (notaNova > 10) {
            throw new IllegalStateException("O valor da nota " + notaNova + " não é valida. Informe um valor entre 0 e 10");
        }
    }

    private DisciplinaMatriculaEntity validarExistenciaMatricula(Long matriculaId) {
        log.info("Validando se matricula existe no banco.");
        if (!disciplinaMatriculaRepository.existsById(matriculaId)) {
            log.error("Matricula não encontrada.");
            throw new NoSuchElementException("Matricula não encontrada com ID: " + matriculaId);
        }
        log.info("Matricula encontrada no banco.");
        return disciplinaMatriculaRepository.findById(matriculaId).get();
    }
}