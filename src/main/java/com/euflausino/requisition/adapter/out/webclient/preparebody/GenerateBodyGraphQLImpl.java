package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class GenerateBodyGraphQLImpl implements GenerateBody {

    private final ObjectMapper objectMapper;

    public GenerateBodyGraphQLImpl() {
        this.objectMapper = new ObjectMapper();
    }

     @Override
    public OutCompletedRequestBodyDTO generate(OutRequestModelDTO request) throws JsonProcessingException {
         Object data = objectMapper.writeValueAsString(request.body().graphQL());
         return new OutCompletedRequestBodyDTO(data, request.bodyType().getType());
    }
}
