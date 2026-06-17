package com.euflausino.requisition.service.gerarCorpoRequisicao;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.euflausino.requisition.entity.enums.TipoRequisicaoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class GerarCorpoFactory{

    private static final Map<TipoRequisicaoEntity, Function<RequisicaoEntity, CorpoRequisicaoPreparadoEntity>>CORPOS = new HashMap<>();

    static {
        CORPOS.put(TipoRequisicaoEntity.BINARY, requisicaoEntity -> new GerarCorpoBinarioImpl().gerar(requisicaoEntity));
        CORPOS.put(TipoRequisicaoEntity.GRAPHQL, requisicaoEntity -> {
            try {
                return new GerarCorpoGraphQLImpl().gerar(requisicaoEntity);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        CORPOS.put(TipoRequisicaoEntity.RAW, requisicaoEntity -> {
            try {
                return new GerarCorpoRawImpl().gerar(requisicaoEntity);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        CORPOS.put(TipoRequisicaoEntity.RAW_HTML, requisicaoEntity -> new GerarCorpoRawLiteralImpl().gerar(requisicaoEntity));
        CORPOS.put(TipoRequisicaoEntity.RAW_JAVASCRIPT, requisicaoEntity -> new GerarCorpoRawLiteralImpl().gerar(requisicaoEntity));
        CORPOS.put(TipoRequisicaoEntity.RAW_XML, requisicaoEntity -> new GerarCorpoRawLiteralImpl().gerar(requisicaoEntity));
        CORPOS.put(TipoRequisicaoEntity.FORM_DATA, requisicaoEntity -> new GerarCorpoFormularioImpl().gerar(requisicaoEntity));
        CORPOS.put(TipoRequisicaoEntity.FORM_URLENCODED, requisicaoEntity -> new GerarCorpoFormularioImpl().gerar(requisicaoEntity));
    }

    public CorpoRequisicaoPreparadoEntity gerarCorpo(TipoRequisicaoEntity tipoRequisicaoEntity, RequisicaoEntity requisicaoEntity) {
        Function<RequisicaoEntity, CorpoRequisicaoPreparadoEntity> corpoStrategy = CORPOS.get(tipoRequisicaoEntity);
        if (corpoStrategy == null) {
            throw new IllegalArgumentException("Tipo de token inválido: " + tipoRequisicaoEntity);
        }
        return corpoStrategy.apply(requisicaoEntity);
    }

}
