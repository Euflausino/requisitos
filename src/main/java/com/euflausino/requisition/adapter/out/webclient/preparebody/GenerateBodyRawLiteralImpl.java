package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;
import com.euflausino.requisition.application.model.FinalRequestBodyModel;
import org.springframework.stereotype.Component;

@Component
public class GenerateBodyRawLiteralImpl implements GenerateBody {

    /**
     * Prepare literal body: HTML, JavaScript, XML to string.
     */
    @Override
    public OutCompletedRequestBodyDTO generate(OutRequestModelDTO requisicao) {
        String contentType = ExtractBody.extract(requisicao.headers(), requisicao.bodyType().getType());

        Object data;
        if (requisicao.body() == null) {
            data = "";
        } else if (requisicao.body().raw() instanceof String) {
            data = requisicao.body().raw();
        } else {
            // Fallback: if is not a string, parse to.
            data = String.valueOf(requisicao.body().raw());
        }

        return new OutCompletedRequestBodyDTO(data, contentType);
    }
}
