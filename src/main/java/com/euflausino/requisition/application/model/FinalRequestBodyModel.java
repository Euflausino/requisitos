package com.euflausino.requisition.application.model;

public class FinalRequestBodyModel {
    private final Object data;
    private final String contentType;

    public FinalRequestBodyModel(Object dados, String tipoConteudo) {
        this.data = dados;
        this.contentType = tipoConteudo;
    }

    public Object getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean temDados() {
        return data != null;
    }
}

