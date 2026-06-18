package com.euflausino.requisition.adapter.out.webclient.dto;

import com.euflausino.requisition.application.model.GraphQLBodyModel;

import java.util.Map;

public record OutCompletedRequestBodyDTO(
        Object data,
        String contentType
) {
}
