package com.euflausino.requisition.service;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.euflausino.requisition.entity.enums.TipoRequisicaoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
public class GerenciaCorpoDaRequisicaoService {

    private final ObjectMapper objectMapper;

    public GerenciaCorpoDaRequisicaoService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CorpoRequisicaoPreparadoEntity prepararCorpo(RequisicaoEntity requisicao) throws JsonProcessingException {
        return switch (requisicao.getBodyType()) {
            case RAW -> prepararBodyRaw(requisicao);
            case RAW_HTML -> prepararBodyLiteral(requisicao, MediaType.TEXT_HTML_VALUE);
            case RAW_JAVASCRIPT -> prepararBodyLiteral(requisicao, "application/javascript");
            case RAW_XML -> prepararBodyLiteral(requisicao, MediaType.APPLICATION_XML_VALUE);
            case FORM_URLENCODED, FORM_DATA -> prepararBodyFormulario(requisicao);
            case BINARY -> prepararBodyBinario(requisicao);
            case GRAPHQL -> prepararBodyGraphQL(requisicao);
            default -> new CorpoRequisicaoPreparadoEntity(null, null);
        };
    }

    /**
     * Prepara corpo RAW: serializa objeto para JSON ou envia string como-é.
     */
    private CorpoRequisicaoPreparadoEntity prepararBodyRaw(RequisicaoEntity requisicao) throws JsonProcessingException {
        String tipoConteudo = extrairTipoConteudo(requisicao.getHeaders(), MediaType.APPLICATION_JSON_VALUE);

        Object dados;
        if (requisicao.getBody() == null) {
            dados = "";
        } else if (requisicao.getBody().getRaw() instanceof String) {
            // Se já é string, envie literalmente (sem serialização adicional)
            dados = requisicao.getBody().getRaw();
        } else {
            // Se é um objeto, serialize para JSON
            dados = objectMapper.writeValueAsString(requisicao.getBody().getRaw());
        }

        return new CorpoRequisicaoPreparadoEntity(dados, tipoConteudo);
    }

    /**
     * Prepara corpo literal: HTML, JavaScript, XML enviados como string pura.
     */
    private CorpoRequisicaoPreparadoEntity prepararBodyLiteral(
            RequisicaoEntity requisicao,
            String tipoConteudoPadrao) {

        String tipoConteudo = extrairTipoConteudo(requisicao.getHeaders(), tipoConteudoPadrao);

        Object dados;
        if (requisicao.getBody() == null) {
            dados = "";
        } else if (requisicao.getBody().getRaw() instanceof String) {
            dados = requisicao.getBody().getRaw();
        } else {
            // Fallback: converter para string se não for string já
            dados = String.valueOf(requisicao.getBody().getRaw());
        }

        return new CorpoRequisicaoPreparadoEntity(dados, tipoConteudo);
    }

    /**
     * Prepara corpo de formulário: form-urlencoded ou form-data (multipart).
     */
    private CorpoRequisicaoPreparadoEntity prepararBodyFormulario(RequisicaoEntity requisicao) {
        MultiValueMap<String, String> formulario = new LinkedMultiValueMap<>();

        if (requisicao.getBody() != null && requisicao.getBody().getFormData() != null) {
            requisicao.getBody().getFormData()
                .forEach((chave, valor) -> formulario.add(chave, String.valueOf(valor)));
        }

        String tipoConteudo = requisicao.getBodyType() == TipoRequisicaoEntity.FORM_URLENCODED
                ? MediaType.APPLICATION_FORM_URLENCODED_VALUE
                : MediaType.MULTIPART_FORM_DATA_VALUE;

        return new CorpoRequisicaoPreparadoEntity(formulario, tipoConteudo);
    }

    /**
     * Prepara corpo binário.
     */
    private CorpoRequisicaoPreparadoEntity prepararBodyBinario(RequisicaoEntity requisicao) {
        byte[] dados = requisicao.getBody() != null ? requisicao.getBody().getBinary() : new byte[0];
        String tipoConteudo = extrairTipoConteudo(requisicao.getHeaders(), "application/octet-stream");
        return new CorpoRequisicaoPreparadoEntity(dados, tipoConteudo);
    }

    /**
     * Prepara corpo GraphQL.
     */
    private CorpoRequisicaoPreparadoEntity prepararBodyGraphQL(RequisicaoEntity requisicao) throws JsonProcessingException {
        Object dados = objectMapper.writeValueAsString(requisicao.getBody().getGraphQL());
        return new CorpoRequisicaoPreparadoEntity(dados, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * Extrai o tipo de conteúdo dos headers ou usa padrão.
     */
    private String extrairTipoConteudo(Map<String, String> headers, String padrao) {
        if (headers != null) {
            return headers.getOrDefault("Content-Type", padrao);
        }
        return padrao;
    }
}
