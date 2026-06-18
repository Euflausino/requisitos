package com.euflausino.requisition.adapter.out.webclient.dto;

import java.util.Map;

public record OutRequestBodyDTO(
        Object raw,
        Map<String,Object> formData,
        byte[] binary,
        OutGraphQLBodyDTO graphQL
) {
}
