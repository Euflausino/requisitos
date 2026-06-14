package com.euflausino.requisition.dto;

import java.util.List;

public record DTOColecao(

        String nome,
        List<DTORequisicao> requisicoes

) {
}
