package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;

import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;
import org.springframework.stereotype.Component;

@Component
public class GenerateBodyBinaryImpl implements GenerateBody {
    /**
     * Prepare binary body.
     */
    @Override
    public OutCompletedRequestBodyDTO generate(OutRequestModelDTO request) {
        byte[] data = request.body() != null ? request.body().binary() : new byte[0];
        String contentType = ExtractBody.extract(request.headers(), request.bodyType().getType());
        return new OutCompletedRequestBodyDTO(data, contentType);
    }
}
