package com.euflausino.requisition.service.gerarCorpoRequisicao;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.euflausino.requisition.entity.enums.TipoRequisicaoEntity;
import org.springframework.stereotype.Component;

@Component
public class GerarCorpoBinarioImpl implements GerarCorpo{
    /**
     * Prepara corpo binário.
     */
    @Override
    public CorpoRequisicaoPreparadoEntity gerar(RequisicaoEntity requisicao) {
        byte[] dados = requisicao.getBody() != null ? requisicao.getBody().getBinary() : new byte[0];
        String tipoConteudo = ExtrairTipoConteudo.extrair(requisicao.getHeaders(), tipoRequisicaoEntity.getType());
        return new CorpoRequisicaoPreparadoEntity(dados, tipoConteudo);
    }
}
