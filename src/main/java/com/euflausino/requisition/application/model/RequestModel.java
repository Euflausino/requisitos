package com.euflausino.requisition.application.model;

import com.euflausino.requisition.application.model.enums.RequestBodyTypeModel;

import java.util.Map;

public class RequestModel {
    private String name;
    private String method;
    private String url;
    private Map<String, String> headers;
    private RequestBodyTypeModel bodyType;
    private RequestBodyModel body;
    private Map<String, String> queryParams;

    public RequestModel(String name, String method, String url, Map<String, String> headers, RequestBodyTypeModel bodyType, RequestBodyModel body, Map<String, String> queryParams) {
        this.name = name;
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.bodyType = bodyType;
        this.body = body;
        this.queryParams = queryParams;
    }

    public RequestModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public RequestBodyTypeModel getBodyType() {
        return bodyType;
    }

    public void setBodyType(RequestBodyTypeModel bodyType) {
        this.bodyType = bodyType;
    }

    public RequestBodyModel getBody() {
        return body;
    }

    public void setBody(RequestBodyModel body) {
        this.body = body;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }
}
