package com.br.fullstack.M1S12.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Error {

    private String codigo;
    private String mensagem;

}