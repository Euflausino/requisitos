package com.euflausino.requisition.entity;

import com.euflausino.requisition.entity.enums.TipoRequisicaoEntity;

import java.util.Map;

public class RequisicaoEntity {

    private String nome;
    private String method;
    private String url;
    private Map<String, String> headers;
    private TipoRequisicaoEntity bodyType;
    private ConteudoRequisicaoEntity body;
    private Map<String, String> queryParams;


    public RequisicaoEntity(String nome,String method, String url, Map<String, String> headers, TipoRequisicaoEntity bodyType,
                            ConteudoRequisicaoEntity body, Map<String, String> queryParams) {
        this.nome = nome;
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.bodyType = bodyType;
        this.body = body;
        this.queryParams = queryParams;
    }

    public RequisicaoEntity() {
    }

    public ConteudoRequisicaoEntity getBody() {
        return body;
    }
    public void setBody(ConteudoRequisicaoEntity body) {
        this.body = body;
    }
    public TipoRequisicaoEntity getBodyType() {
        return bodyType;
    }
    public void setBodyType(TipoRequisicaoEntity bodyType) {
        this.bodyType = bodyType;
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
    public Map<String, String> getQueryParams() {
        return queryParams;
    }
    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
