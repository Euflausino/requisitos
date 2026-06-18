package com.euflausino.requisition.application.model;

import java.util.Map;

public class RequestBodyModel {

    private Object raw;
    private Map<String,Object> formData;
    private byte[] binary;
    private GraphQLBodyModel graphQL;

    public RequestBodyModel(Object raw, Map<String, Object> formData, byte[] binary, GraphQLBodyModel graphQL) {
        this.raw = raw;
        this.formData = formData;
        this.binary = binary;
        this.graphQL = graphQL;
    }

    public RequestBodyModel() {}

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

    public GraphQLBodyModel getGraphQL() {
        return graphQL;
    }

    public void setGraphQL(GraphQLBodyModel graphQL) {
        this.graphQL = graphQL;
    }

}
