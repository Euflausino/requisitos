package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;

import org.springframework.stereotype.Component;

@Component
public class GenerateBodyRawLiteralImpl implements GenerateBody {

    @Override
    public OutCompletedRequestBodyDTO generate(OutRequestModelDTO requisicao) {
        String contentType = ExtractBody.extract(requisicao.headers(), requisicao.bodyType().getType());
        Object data;
        if (requisicao.body() == null) {
            data = "";
        } else if (isBodyInstaceOfString(requisicao.body())) {
            data = requisicao.body().raw();
        } else {
            data = String.valueOf(requisicao.body().raw());
        }
        return new OutCompletedRequestBodyDTO(data, contentType);
    }

    private boolean isBodyInstaceOfString(OutRequestBodyDTO body){
        return body.raw() instanceof String;
    }
}
