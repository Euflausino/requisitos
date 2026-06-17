package com.euflausino.requisition.service.gerarCorpoRequisicao;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface GerarCorpo {
    CorpoRequisicaoPreparadoEntity gerar (RequisicaoEntity requisicaoEntity) throws JsonProcessingException;
}
