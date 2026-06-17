package com.euflausino.requisition.entity.enums;

import org.springframework.http.MediaType;

public enum TipoRequisicaoEntity {


    NONE(null),
    FORM_DATA(MediaType.MULTIPART_FORM_DATA_VALUE),
    FORM_URLENCODED(MediaType.APPLICATION_FORM_URLENCODED_VALUE),
    RAW(MediaType.APPLICATION_JSON_VALUE),
    RAW_HTML( MediaType.TEXT_HTML_VALUE),
    RAW_JAVASCRIPT("application/javascript"),
    RAW_XML(MediaType.TEXT_XML_VALUE),
    BINARY(MediaType.APPLICATION_OCTET_STREAM_VALUE),
    GRAPHQL(MediaType.APPLICATION_JSON_VALUE);

    private final String type;

    TipoRequisicaoEntity(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
