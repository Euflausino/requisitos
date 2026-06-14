package com.euflausino.requisition.entity;

import java.util.List;

public class ColecaoEntity {

    private String nome;
    private List<RequisicaoEntity> requisicoes;

    public ColecaoEntity(String nome, List<RequisicaoEntity> requisicoes) {
        this.nome = nome;
        this.requisicoes = requisicoes;
    }

    public ColecaoEntity() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<RequisicaoEntity> getRequisicoes() {
        return requisicoes;
    }

    public void setRequisicoes(List<RequisicaoEntity> requisicoes) {
        this.requisicoes = requisicoes;
    }
}
