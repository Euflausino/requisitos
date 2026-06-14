package com.euflausino.requisition.dto.mapper;

import com.euflausino.requisition.dto.DTOColecao;
import com.euflausino.requisition.entity.ColecaoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;

import java.util.List;

public class ColecaoMapper {

    public static ColecaoEntity mapDTOColecaoParaEntity(DTOColecao dtoColecao) {
        return new ColecaoEntity(
                dtoColecao.nome(),
                dtoColecao.requisicoes().stream()
                        .map(RequisicaoMapper::DTORequisicaoParaEntidade)
                        .toList()
        );
    }
    public static DTOColecao mapEntityDTOColecao(ColecaoEntity colecao) {
        return new DTOColecao(
                colecao.getNome(),
                colecao.getRequisicoes().stream()
                        .map(RequisicaoMapper::entidadeRequisicaoParaDTO)
                        .toList()
        );
    }

}
