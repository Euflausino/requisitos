package com.euflausino.requisition.adapter.in.dto;

import com.euflausino.requisition.application.model.GraphQLBodyModel;

import java.util.Map;

public record RequestBodyDTO(
         Object raw,
         Map<String,Object>formData,
         byte[] binary,
         GraphQLBodyDTO graphQL
){
}
