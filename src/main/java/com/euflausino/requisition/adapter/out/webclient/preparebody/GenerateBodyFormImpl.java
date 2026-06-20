package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class GenerateBodyFormImpl implements GenerateBody {
    @Override
    public OutCompletedRequestBodyDTO generate(OutRequestModelDTO request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        if (request.body().formData() != null) {
            request.body().formData()
                    .forEach((key, value) -> form.add(key, String.valueOf(value)));
        }
        String contentType = ExtractBody.extract(request.headers(), request.bodyType().getType());
        return new OutCompletedRequestBodyDTO(form, contentType);
    }
}
