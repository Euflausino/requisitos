package com.euflausino.requisition.service.gerarCorpoRequisicao;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class GerarCorpoGraphQLImpl implements GerarCorpo{

    private final ObjectMapper objectMapper;

    public GerarCorpoGraphQLImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GerarCorpoGraphQLImpl() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Prepara corpo GraphQL.
     */
     @Override
    public CorpoRequisicaoPreparadoEntity gerar(RequisicaoEntity requisicao) throws JsonProcessingException {
         Object dados = objectMapper.writeValueAsString(requisicao.getBody().getGraphQL());
         return new CorpoRequisicaoPreparadoEntity(dados, requisicao.getBodyType().getType());
    }
}
