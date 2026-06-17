package com.euflausino.requisition.service.gerarCorpoRequisicao;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExtrairTipoConteudo {
    public static String extrair(Map<String, String> headers, String padrao) {
        if (headers != null) {
            return headers.getOrDefault("Content-Type", padrao);
        }
        return padrao;
    }
}
