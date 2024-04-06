package com.br.fullstack.M1S12.service;


import com.br.fullstack.M1S12.controller.dto.request.DisciplinaMatriculaRequest;
import com.br.fullstack.M1S12.entity.AlunoEntity;
import com.br.fullstack.M1S12.entity.DisciplinaEntity;
import com.br.fullstack.M1S12.entity.DisciplinaMatriculaEntity;
import com.br.fullstack.M1S12.repository.AlunoRepository;
import com.br.fullstack.M1S12.repository.DisciplinaMatriculaRepository;
import com.br.fullstack.M1S12.repository.DisciplinaRepository;
import com.br.fullstack.M1S12.repository.NotasRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class DisciplinaMatriculaService {

    @Autowired
    private DisciplinaMatriculaRepository disciplinaMatriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private NotasRepository notasRepository;

    private static final Logger logger = LoggerFactory.getLogger(DisciplinaMatriculaService.class);

    public DisciplinaMatriculaEntity criarDisciplinaMatricula(DisciplinaMatriculaRequest disciplinaMatricula) {
        logger.info("Iniciado processo de realizacao de matricula. Dados recebidos {}", disciplinaMatricula);
        DisciplinaMatriculaEntity disciplinaMatriculaEntity = validaCriacaoDisciplinaMatricula(disciplinaMatricula);

        logger.info("Iniciando tentativa de salvar entidade no banco: {}", disciplinaMatriculaEntity);

        DisciplinaMatriculaEntity disciplinaMatriculaEntityRetorno = disciplinaMatriculaRepository.save(disciplinaMatriculaEntity);

        logger.info("Finalizado processo de realizacao de matricula. Dados retornados {}", disciplinaMatriculaEntityRetorno);
        return disciplinaMatriculaEntityRetorno;
    }

    private DisciplinaMatriculaEntity validaCriacaoDisciplinaMatricula(DisciplinaMatriculaRequest disciplinaMatriculaRequest) {
        logger.info("Iniciado processo de validacao da criação da Matricula");

        if (disciplinaMatriculaRepository.matriculaExisteParaAlunoNaDisciplina(disciplinaMatriculaRequest.alunoId(), disciplinaMatriculaRequest.disciplinaId())) {
            logger.error("Validação falhou: matricula já existente na disciplina para este aluno.");
            throw new IllegalStateException("Matricula já existente para o aluno de ID " + disciplinaMatriculaRequest.alunoId() + " na disciplina com ID " + disciplinaMatriculaRequest.disciplinaId());
        }
        logger.error("Matricula não existente na disciplina para este aluno validada com sucesso.");

        validaSeAlunoExiste(disciplinaMatriculaRequest.alunoId());

        Optional<AlunoEntity> aluno = alunoRepository.findById(disciplinaMatriculaRequest.alunoId());
        logger.info("Entidade aluno recuperada do banco: {}", aluno);

        validaSeDisciplinaExiste(disciplinaMatriculaRequest.disciplinaId());

        Optional<DisciplinaEntity> disciplina = disciplinaRepository.findById(disciplinaMatriculaRequest.disciplinaId());
        logger.info("Entidade disciplina recuperada do banco: {}", disciplina);

        return mapDtoToEntity(disciplinaMatriculaRequest, aluno.get(), disciplina.get());
    }

    private DisciplinaMatriculaEntity mapDtoToEntity(DisciplinaMatriculaRequest disciplinaMatriculaRequest, AlunoEntity aluno, DisciplinaEntity disciplina) {
        logger.info("Iniciado processo de mapeamento do DTO para Entity");
        DisciplinaMatriculaEntity disciplinaMatriculaEntity = new DisciplinaMatriculaEntity(aluno, disciplina, LocalDate.now(), disciplinaMatriculaRequest.mediaFinal());
        logger.info("Finalizado processo de mapeamento do DTO para Entity. Retornando entidade: {}", disciplinaMatriculaEntity);
        return disciplinaMatriculaEntity;
    }

    public ResponseEntity<?> deletarDisciplinaMatricula(Long id) {
        logger.info("Iniciado processo de deleção de matricula. Id recebido {}", id);
        validaDelecaoDisciplinaMatricula(id);
        logger.info("Iniciando tentativa de deleção entidade no banco com ID: {}", id);
        disciplinaMatriculaRepository.deleteById(id);
        logger.info("Finalizado processo de deleção da matricula.");
        return ResponseEntity.noContent().build();
    }

    private void validaDelecaoDisciplinaMatricula(Long id) {
        logger.info("Iniciado processo de validacao de deleção da matricula");

        if (!disciplinaMatriculaRepository.existsById(id)) {
            logger.error("Validação falhou: matricula não encontrada.");
            throw new NoSuchElementException("Matricula não encontrada com ID: " + id);
        }
        logger.info("Existencia da matricula validada com sucesso.");

        if (notasRepository.temNotaDefinida(id)) {
            logger.error("Validação falhou: matricula já possui notas definidas.");
            throw new IllegalStateException("Matricula com ID " + id + " já possui nota(s)! Não é possível realizar a deleção neste caso.");
        }
        logger.info("Possibilidade da exclusão da matricula validada com sucesso.");
    }

    public List<DisciplinaMatriculaEntity> listarDisciplinaMatricula() {
        return disciplinaMatriculaRepository.findAll();
    }

    public ResponseEntity<?> buscarDisciplinaMatriculaPorId(Long matriculaId) {
        logger.info("Iniciado processo de busca da matricula de ID: {}", matriculaId);

        if (!disciplinaMatriculaRepository.existsById(matriculaId)) {
            logger.error("Matricula não encontrada.");
            throw new NoSuchElementException("Matricula não encontrado com ID: " + matriculaId);
        }

        Optional<DisciplinaMatriculaEntity> matricula = disciplinaMatriculaRepository.findById(matriculaId);
        logger.info("Entidade da matricula recuperada do banco: {}", matricula.get());

        return ResponseEntity.ok(matricula.get());
    }

    public List<DisciplinaMatriculaEntity> buscarTodasDisciplinaMatriculasPorAlunoId(Long alunoId) {
        logger.info("Iniciado processo de busca de todas as matriculas do aluno de ID: {}", alunoId);

        validaSeAlunoExiste(alunoId);

        logger.info("Iniciando busca por matriculas do aluno no banco com ID: {}", alunoId);
        List<DisciplinaMatriculaEntity> listMatriculas = disciplinaMatriculaRepository.buscaTodasPorAlunoId(alunoId);
        if (listMatriculas.isEmpty()) {
            logger.info("Não foram encontradas matrículas para o aluno com ID {}", alunoId);
            throw new NoSuchElementException("Aluno de ID " + alunoId + " não possui matrículas");
        } else
            logger.info("Matriculas do aluno com ID {} recuperadas do banco: {}", alunoId, listMatriculas);

        return listMatriculas;
    }

    private void validaSeAlunoExiste(Long alunoId) {
        logger.info("Validando se aluno existe no banco.");

        if (!alunoRepository.existsById(alunoId)) {
            logger.error("Aluno não encontrado.");
            throw new NoSuchElementException("Aluno não encontrado com ID: " + alunoId);
        }
        logger.info("Aluno encontrado no banco.");
    }

    public List<DisciplinaMatriculaEntity> buscarTodasDisciplinaMatriculasPorDisciplinaId(Long disciplinaId) {
        logger.info("Iniciado processo de busca de todas as matriculas da disciplina de ID: {}", disciplinaId);

        validaSeDisciplinaExiste(disciplinaId);

        logger.info("Iniciando busca por matriculas da disciplina no banco com ID: {}", disciplinaId);
        List<DisciplinaMatriculaEntity> listMatriculas = disciplinaMatriculaRepository.buscaTodasPorDisciplinaId(disciplinaId);
        if (listMatriculas.isEmpty()) {
            logger.info("Não foram encontradas matrículas para a disciplina com ID {}", disciplinaId);
            throw new NoSuchElementException("Disciplina de ID " + disciplinaId + " não possui matrículas");
        } else
            logger.info("Matriculas da disciplina com ID {} recuperadas do banco: {}", disciplinaId, listMatriculas);

        return listMatriculas;
    }

    private void validaSeDisciplinaExiste(Long disciplinaId) {
        logger.info("Validando se disciplina existe no banco.");

        if (!disciplinaRepository.existsById(disciplinaId)) {
            logger.error("Disciplina não encontrada.");
            throw new NoSuchElementException("Disciplina não encontrado com ID: " + disciplinaId);
        }
        logger.info("Disciplina encontrada no banco.");
    }
}
