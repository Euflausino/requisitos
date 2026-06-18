package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;
import com.euflausino.requisition.application.model.FinalRequestBodyModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class GenerateBodyRawImpl implements GenerateBody {

    private final ObjectMapper objectMapper;

    public GenerateBodyRawImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GenerateBodyRawImpl() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Prepare RAW body: parse object to JSON or string.
     */
    @Override
    public OutCompletedRequestBodyDTO generate(OutRequestModelDTO request) throws JsonProcessingException {
        String contentType = ExtractBody.extract(request.headers(), request.bodyType().getType());

        Object data;
        if (request.body() == null) {
            data = "";
        } else if (request.body().raw() instanceof String) {
            // if is string, send literally (with no one serialization additional)
            data = request.body().raw();
        } else {
            // if is an object, serialize to JSON
            data = objectMapper.writeValueAsString(request.body().raw());
        }

        return new OutCompletedRequestBodyDTO(data, contentType);
    }
}
