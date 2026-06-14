package com.euflausino.requisition.entity;

import java.util.Map;

public class ConteudoRequisicaoEntity {

    private Object raw;
    private Map<String,Object> formData;
    private byte[] binary;
    private GraphQLBodyEntity graphQL;

    public ConteudoRequisicaoEntity(Object raw, Map<String, Object> formData, byte[] binary, GraphQLBodyEntity graphQL) {
        this.raw = raw;
        this.formData = formData;
        this.binary = binary;
        this.graphQL = graphQL;
    }

    public ConteudoRequisicaoEntity() {}

    public Object getRaw() {
        return raw;
    }

    public void setRaw(Object raw) {
        this.raw = raw;
    }

    public Map<String, Object> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, Object> formData) {
        this.formData = formData;
    }

    public byte[] getBinary() {
        return binary;
    }

    public void setBinary(byte[] binary) {
        this.binary = binary;
    }

    public GraphQLBodyEntity getGraphQL() {
        return graphQL;
    }

    public void setGraphQL(GraphQLBodyEntity graphQL) {
        this.graphQL = graphQL;
    }

}
