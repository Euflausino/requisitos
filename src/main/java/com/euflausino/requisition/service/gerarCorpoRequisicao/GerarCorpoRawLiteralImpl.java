package com.euflausino.requisition.service.gerarCorpoRequisicao;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import org.springframework.stereotype.Component;

@Component
public class GerarCorpoRawLiteralImpl implements GerarCorpo {

    /**
     * Prepara corpo literal: HTML, JavaScript, XML enviados como string pura.
     */
    @Override
    public CorpoRequisicaoPreparadoEntity gerar(RequisicaoEntity requisicao) {
        String tipoConteudo = ExtrairTipoConteudo.extrair(requisicao.getHeaders(), requisicao.getBodyType().getType());

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
}
