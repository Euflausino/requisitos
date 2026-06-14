package com.euflausino.requisition.dto;

import java.util.Map;

public record DTORequisicao(

        String nome,
        String method,
        String url,
        Map<String, String>headers,
        DTOTipoRequisicao bodyType,
        DTOConteudoRequisicao body,
        Map<String, String> queryParams
) {
}
