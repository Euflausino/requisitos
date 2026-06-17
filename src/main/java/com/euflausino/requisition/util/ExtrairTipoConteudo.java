package com.euflausino.requisition.util;

import java.util.Map;

public class ExtrairTipoConteudo {
    public static String extrair(Map<String, String> headers, String padrao){
        if (headers != null) {
            return headers.getOrDefault("Content-Type", padrao);
        }
        return padrao;
    }
}
