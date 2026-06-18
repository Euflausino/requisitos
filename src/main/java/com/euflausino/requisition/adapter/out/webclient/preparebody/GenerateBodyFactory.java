package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.enums.OutRequestBodyTypeDTO;
import com.euflausino.requisition.application.model.FinalRequestBodyModel;
import com.euflausino.requisition.application.model.enums.RequestBodyTypeModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class GenerateBodyFactory {

    private static final Map<OutRequestBodyTypeDTO, Function<OutRequestModelDTO, OutCompletedRequestBodyDTO>> BODY = new HashMap<>();

    static {
        BODY.put(OutRequestBodyTypeDTO.NONE, request -> new OutCompletedRequestBodyDTO(null,  null));
        BODY.put(OutRequestBodyTypeDTO.BINARY, request -> new GenerateBodyBinaryImpl().generate(request));
        BODY.put(OutRequestBodyTypeDTO.GRAPHQL, request -> {
            try {
                return new GenerateBodyGraphQLImpl().generate(request);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        BODY.put(OutRequestBodyTypeDTO.RAW, request -> {
            try {
                return new GenerateBodyRawImpl().generate(request);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        BODY.put(OutRequestBodyTypeDTO.RAW_HTML, request -> new GenerateBodyRawLiteralImpl().generate(request));
        BODY.put(OutRequestBodyTypeDTO.RAW_JAVASCRIPT, request -> new GenerateBodyRawLiteralImpl().generate(request));
        BODY.put(OutRequestBodyTypeDTO.RAW_XML, request -> new GenerateBodyRawLiteralImpl().generate(request));
        BODY.put(OutRequestBodyTypeDTO.FORM_DATA, request -> new GenerateBodyFormImpl().generate(request));
        BODY.put(OutRequestBodyTypeDTO.FORM_URLENCODED, request -> new GenerateBodyFormImpl().generate(request));
    }

    public OutCompletedRequestBodyDTO generateBody(OutRequestBodyTypeDTO requestBodyTypeModel, OutRequestModelDTO outRequestModelDTO) {
        Function<OutRequestModelDTO, OutCompletedRequestBodyDTO> bodyStrategy = BODY.get(requestBodyTypeModel);
        if (bodyStrategy == null) {
            throw new IllegalArgumentException("Invalid Body Type: " + requestBodyTypeModel);
        }
        return bodyStrategy.apply(outRequestModelDTO);
    }

}
