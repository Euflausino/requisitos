package com.euflausino.requisition.application.model;

import java.util.List;

public class CollectionModel {

    private String name;
    private List<RequestModel> requests;

    public CollectionModel(String nome, List<RequestModel> requisicoes) {
        this.name = nome;
        this.requests = requisicoes;
    }

    public CollectionModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RequestModel> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestModel> requests) {
        this.requests = requests;
    }
}
