package com.euflausino.requisition.service;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.euflausino.requisition.entity.TipoRequisicaoEntity;
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
            case FORM_URLENCODED, FORM_DATA -> prepararBodyFormulario(requisicao);
            case BINARY -> prepararBodyBinario(requisicao);
            case GRAPHQL -> prepararBodyGraphQL(requisicao);
            default -> new CorpoRequisicaoPreparadoEntity(null, null);
        };
    }

    private CorpoRequisicaoPreparadoEntity prepararBodyRaw(RequisicaoEntity requisicao) throws JsonProcessingException {
        String tipoConteudo = extrairTipoConteudo(requisicao.getHeaders(), "application/json");
        Object dados = requisicao.getBody() == null ? "" : objectMapper.writeValueAsString(requisicao.getBody().getRaw());
        return new CorpoRequisicaoPreparadoEntity(dados, tipoConteudo);
    }

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

    private CorpoRequisicaoPreparadoEntity prepararBodyBinario(RequisicaoEntity requisicao) {
        byte[] dados = requisicao.getBody() != null ? requisicao.getBody().getBinary() : new byte[0];
        String tipoConteudo = extrairTipoConteudo(requisicao.getHeaders(), "application/octet-stream");
        return new CorpoRequisicaoPreparadoEntity(dados, tipoConteudo);
    }

    private CorpoRequisicaoPreparadoEntity prepararBodyGraphQL(RequisicaoEntity requisicao) throws JsonProcessingException {
        Object dados = objectMapper.writeValueAsString(requisicao.getBody().getGraphQL());
        return new CorpoRequisicaoPreparadoEntity(dados, MediaType.APPLICATION_JSON_VALUE);
    }

    private String extrairTipoConteudo(Map<String, String> headers, String padrao) {
        if (headers != null) {
            return headers.getOrDefault("Content-Type", padrao);
        }
        return padrao;
    }
}
