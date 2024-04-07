package com.br.fullstack.M1S12.service;

import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import com.br.fullstack.M1S12.entity.NotasEntity;
import com.br.fullstack.M1S12.exception.NotFoundException;
import com.br.fullstack.M1S12.repository.DisciplinaMatriculaRepository;
import com.br.fullstack.M1S12.repository.NotasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotasService {

    private final NotasRepository notasRepository;
    private final DisciplinaMatriculaRepository disciplinaMatriculaRepository;

    private static final Logger logger = LoggerFactory.getLogger(DisciplinaMatriculaService.class);

    public ResponseEntity<?> deletarNota(Long id) {
        log.info("Processo de deleção de nota iniciado. ID recebido {}", id);

        log.info("Tentativa de busca por nota em matrículas com o ID " + id + " para a exclusão...");
        DisciplinaMatriculaEntity matricula = notasRepository.findById(id).map(NotasEntity::getDisciplina_matricula_id).orElseThrow(
                () -> new NotFoundException("Nota não encontrada com o ID: " + id));
        log.info("Nota encontrada e com matrícula associada. ID: " + id);

        log.info("Atualizando os dados para recalcular a média final...");
        double coeficiente = notasRepository.findById(id).get().getCoeficiente();
        double valorNota = notasRepository.findById(id).get().getNota();
        double novaMediaFinal = recalcularMediaFinal(matricula, coeficiente, valorNota);

        log.info("Salvando matrícula atualizada no banco de dados...");
        matricula.setMediaFinal(novaMediaFinal);
        disciplinaMatriculaRepository.save(matricula);
        log.info("Matrícula atualizada salva!");

        notasRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private double recalcularMediaFinal(DisciplinaMatriculaEntity matricula, double coeficienteExcluido, double valorNotaExcluida) {
        log.info("Recalculando média final...");
        double mediaFinalAtual = matricula.getMediaFinal();
        double somaNotasAtuais = mediaFinalAtual * (1 - coeficienteExcluido);
        double novaSomaNotas = somaNotasAtuais - (coeficienteExcluido * valorNotaExcluida);
        log.info("Cálculo da média atualizado com sucesso.");
        return novaSomaNotas / (1 - coeficienteExcluido);
    }
}
