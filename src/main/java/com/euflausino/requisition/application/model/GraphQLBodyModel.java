package com.euflausino.requisition.application.model;

import java.util.Map;

public class GraphQLBodyModel {

    private String query;
    private Map<String, Object> variables;

    public GraphQLBodyModel(String query, Map<String, Object> variables) {
        this.query = query;
        this.variables = variables;
    }

    public GraphQLBodyModel() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }



}
