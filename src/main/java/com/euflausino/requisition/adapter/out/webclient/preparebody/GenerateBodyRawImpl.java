package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class GenerateBodyRawImpl implements GenerateBody {

    private final ObjectMapper objectMapper;

    public GenerateBodyRawImpl() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public OutCompletedRequestBodyDTO generate(OutRequestModelDTO request) throws JsonProcessingException {
        String contentType = ExtractBody.extract(request.headers(), request.bodyType().getType());
        Object data;
        if (request.body() == null) {
            data = "";
        } else if (isBodyInstanceOfString(request.body())) {
            data = request.body().raw();
        } else {
            data = objectMapper.writeValueAsString(request.body().raw());
        }
        return new OutCompletedRequestBodyDTO(data, contentType);
    }

    private boolean isBodyInstanceOfString(OutRequestBodyDTO body) {
        return body.raw() instanceof String;
    }

}
