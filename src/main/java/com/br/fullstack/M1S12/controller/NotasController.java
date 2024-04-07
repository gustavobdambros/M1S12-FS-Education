package com.br.fullstack.M1S12.controller;

import com.br.fullstack.M1S12.service.NotasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("nota")
public class NotasController {

    private final NotasService notasService;

    private static Logger logger = LoggerFactory.getLogger(AlunoController.class);

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarNota(@PathVariable Long id){
        log.info("Chamada para DELETE no endpoint /nota/{id} recebida. ID: " + id);
        ResponseEntity<?> responseEntity = notasService.deletarNota(id);
        log.info("Finalizada chamada DELETE no endpoint /nota/{id}. Nota com o id " + id + "deletada com sucesso.");
        return responseEntity;
    }
}
