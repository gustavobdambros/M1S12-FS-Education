package com.br.fullstack.M1S12.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {

    private String codigo;
    private String mensagem;

}