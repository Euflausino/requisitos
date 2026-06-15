package com.euflausino.requisition.dto.enums;

/**
 * Tipos de corpo de requisição suportados.
 *
 * RAW — JSON ou objeto genérico (será serializado para JSON)
 * RAW_HTML — HTML literal (enviado como-é com Content-Type: text/html)
 * RAW_JAVASCRIPT — JavaScript literal (enviado como-é com Content-Type: application/javascript)
 * RAW_XML — XML literal (enviado como-é com Content-Type: application/xml)
 */
public enum DTOTipoRequisicao {
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
