package com.euflausino.requisition.entity;

public class CorpoRequisicaoPreparadoEntity {
    private final Object dados;
    private final String tipoConteudo;

    public CorpoRequisicaoPreparadoEntity(Object dados, String tipoConteudo) {
        this.dados = dados;
        this.tipoConteudo = tipoConteudo;
    }

    public Object getDados() {
        return dados;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public boolean temDados() {
        return dados != null;
    }
}

