package com.euflausino.requisition.service.gerarCorpoRequisicao;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class GerarCorpoRawImpl implements GerarCorpo {

    private final ObjectMapper objectMapper;

    public GerarCorpoRawImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GerarCorpoRawImpl() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Prepara corpo RAW: serializa objeto para JSON ou envia string como-é.
     */
    @Override
    public CorpoRequisicaoPreparadoEntity gerar(RequisicaoEntity requisicao) throws JsonProcessingException {
        String tipoConteudo = ExtrairTipoConteudo.extrair(requisicao.getHeaders(), requisicao.getBodyType().getType());

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
}
