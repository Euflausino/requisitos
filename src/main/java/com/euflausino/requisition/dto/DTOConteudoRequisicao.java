package com.euflausino.requisition.dto;

import com.euflausino.requisition.entity.GraphQLBodyEntity;

import java.util.Map;

public record DTOConteudoRequisicao (
         Object raw,
         Map<String,Object>formData,
         byte[] binary,
         GraphQLBodyEntity graphQL
){
}
