package com.euflausino.requisition.adapter.in.dto.mapper;

import com.euflausino.requisition.adapter.in.dto.GraphQLBodyDTO;
import com.euflausino.requisition.adapter.in.dto.RequestBodyDTO;
import com.euflausino.requisition.adapter.in.dto.RequestDTO;
import com.euflausino.requisition.adapter.in.dto.enums.RequestBodyTypeDTO;
import com.euflausino.requisition.application.model.GraphQLBodyModel;
import com.euflausino.requisition.application.model.RequestBodyModel;
import com.euflausino.requisition.application.model.RequestModel;
import com.euflausino.requisition.application.model.enums.RequestBodyTypeModel;

public class RequestMapper {

    public static RequestModel mapRequestDTOToModel(RequestDTO requestDto) {
        return new RequestModel(
                requestDto.name(),
                requestDto.method(),
                requestDto.url(),
                requestDto.headers(),
                mapRequestBodyTypeDTOToEntity(requestDto.bodyType()),
                requestDto.body() == null ? null : mapRequestBodyDTOToEntity(requestDto.body()),
                requestDto.queryParams()
        );
    }
    public static RequestDTO mapRequestModelToDTO(RequestModel requestModel) {
        return new RequestDTO(
                requestModel.getName(),
                requestModel.getMethod(),
                requestModel.getUrl(),
                requestModel.getHeaders(),
                mapRequestBodyTypeToDTO(requestModel.getBodyType()),
                requestModel.getBody() == null ? null : mapRequestBodyModelToDTO(requestModel.getBody()),
                requestModel.getQueryParams()
        );
    }

    private static RequestBodyTypeDTO mapRequestBodyTypeToDTO(RequestBodyTypeModel dtoTipoRequisicao) {
        return RequestBodyTypeDTO.valueOf(dtoTipoRequisicao.toString());
    }

    private static RequestBodyTypeModel mapRequestBodyTypeDTOToEntity(RequestBodyTypeDTO requestBodyTypeDto) {
        return RequestBodyTypeModel.valueOf(requestBodyTypeDto.toString());
    }

    private static RequestBodyModel mapRequestBodyDTOToEntity(RequestBodyDTO conteudoRequisicao) {
        return new RequestBodyModel(
                conteudoRequisicao.raw(),
                conteudoRequisicao.formData(),
                conteudoRequisicao.binary(),
                mapGraphQLBodyDTOToEntity(conteudoRequisicao.graphQL())
        );
    }

    private static RequestBodyDTO mapRequestBodyModelToDTO(RequestBodyModel conteudoRequisicaoEntity) {
        return new RequestBodyDTO(
                conteudoRequisicaoEntity.getRaw(),
                conteudoRequisicaoEntity.getFormData(),
                conteudoRequisicaoEntity.getBinary(),
                mapGraphQLBodyModelToDTO(conteudoRequisicaoEntity.getGraphQL())
        );
    }

    private static GraphQLBodyDTO mapGraphQLBodyModelToDTO(GraphQLBodyModel graphQLBodyModel) {
        return new GraphQLBodyDTO(
                graphQLBodyModel.getQuery(),
                graphQLBodyModel.getVariables()
        );
    }

     private static GraphQLBodyModel mapGraphQLBodyDTOToEntity(GraphQLBodyDTO graphQLBodyDTO) {
        return new GraphQLBodyModel(
                graphQLBodyDTO.query(),
                graphQLBodyDTO.variables()
        );
    }

}
