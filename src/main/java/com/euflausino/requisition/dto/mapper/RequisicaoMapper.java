package com.euflausino.requisition.dto.mapper;

import com.euflausino.requisition.dto.DTOConteudoRequisicao;
import com.euflausino.requisition.dto.DTORequisicao;
import com.euflausino.requisition.dto.DTOTipoRequisicao;
import com.euflausino.requisition.entity.ConteudoRequisicaoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.euflausino.requisition.entity.TipoRequisicaoEntity;

public class RequisicaoMapper {

    public static RequisicaoEntity DTORequisicaoParaEntidade(DTORequisicao dtoRequisicao) {
        return new RequisicaoEntity(
                dtoRequisicao.nome(),
                dtoRequisicao.method(),
                dtoRequisicao.url(),
                dtoRequisicao.headers(),
                mapDTOTipoRequisicaoParaEntidade(dtoRequisicao.bodyType()),
                dtoRequisicao.body() == null ? null : mapDTOConteudoRequisicaoParaEntidade(dtoRequisicao.body()),
                dtoRequisicao.queryParams()
        );
    }
    public static DTORequisicao entidadeRequisicaoParaDTO(RequisicaoEntity requisicaoEntity) {
        return new DTORequisicao(
                requisicaoEntity.getNome(),
                requisicaoEntity.getMethod(),
                requisicaoEntity.getUrl(),
                requisicaoEntity.getHeaders(),
                mapTipoRequisicaoEntityParaDTO(requisicaoEntity.getBodyType()),
                requisicaoEntity.getBody() == null ? null : mapEntidadeConteudoRequisicaoParaDTO(requisicaoEntity.getBody()),
                requisicaoEntity.getQueryParams()
        );
    }

    private static DTOTipoRequisicao mapTipoRequisicaoEntityParaDTO(TipoRequisicaoEntity dtoTipoRequisicao) {
        return DTOTipoRequisicao.valueOf(dtoTipoRequisicao.toString());
    }

    private static TipoRequisicaoEntity mapDTOTipoRequisicaoParaEntidade(DTOTipoRequisicao dtoTipoRequisicao) {
        return TipoRequisicaoEntity.valueOf(dtoTipoRequisicao.toString());
    }

    private static ConteudoRequisicaoEntity mapDTOConteudoRequisicaoParaEntidade(DTOConteudoRequisicao conteudoRequisicao) {
        return new ConteudoRequisicaoEntity(
                conteudoRequisicao.raw(),
                conteudoRequisicao.formData(),
                conteudoRequisicao.binary(),
                conteudoRequisicao.graphQL()
        );
    }

    private static DTOConteudoRequisicao mapEntidadeConteudoRequisicaoParaDTO(ConteudoRequisicaoEntity conteudoRequisicaoEntity) {
        return new DTOConteudoRequisicao(
                conteudoRequisicaoEntity.getRaw(),
                conteudoRequisicaoEntity.getFormData(),
                conteudoRequisicaoEntity.getBinary(),
                conteudoRequisicaoEntity.getGraphQL()
        );
    }

}
