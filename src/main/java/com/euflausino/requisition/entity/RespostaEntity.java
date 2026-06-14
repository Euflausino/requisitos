package com.euflausino.requisition.entity;

import java.util.List;
import java.util.Map;

public class RespostaEntity {

    private Integer status;
    private String statusText;
    private Boolean successful;
    private Map<String, List<String>> headers;
    private String body;
    private Long responseTimeMs;
    private Long bodySizeBytes;
    private Long headersSizeBytes;

    public RespostaEntity(Integer status, String statusText, Boolean successful, Map<String, List<String>> headers, String body, Long responseTimeMs, Long sizeBytes, Long headersSizeBytes) {
        this.status = status;
        this.statusText = statusText;
        this.successful = successful;
        this.headers = headers;
        this.body = body;
        this.responseTimeMs = responseTimeMs;
        this.bodySizeBytes = sizeBytes;
        this.headersSizeBytes = headersSizeBytes;
    }

    public RespostaEntity() {
    }

    public Long getHeadersSizeBytes() {
        return headersSizeBytes;
    }

    public void setHeadersSizeBytes(Long headersSizeBytes) {
        this.headersSizeBytes = headersSizeBytes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public Long getBodySizeBytes() {
        return bodySizeBytes;
    }

    public void setBodySizeBytes(Long bodySizeBytes) {
        this.bodySizeBytes = bodySizeBytes;
    }
}
