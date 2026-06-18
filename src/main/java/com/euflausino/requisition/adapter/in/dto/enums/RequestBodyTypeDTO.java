package com.euflausino.requisition.adapter.in.dto.enums;

/**
 * Body type allowed.
 *
 * RAW — JSON or generic object (parse to JSON)
 * RAW_HTML — HTML literal (send like are with Content-Type: text/html)
 * RAW_JAVASCRIPT — JavaScript literal (send like are with com Content-Type: application/javascript)
 * RAW_XML — XML literal (send like are with Content-Type: application/xml)
 */
public enum RequestBodyTypeDTO {
    NONE,
    FORM_DATA,
    FORM_URLENCODED,
    RAW,
    RAW_HTML,
    RAW_JAVASCRIPT,
    RAW_XML,
    BINARY,
    GRAPHQL
}
