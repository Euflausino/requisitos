package com.euflausino.requisition.adapter.out.webclient.dto;

import java.util.Map;

public record OutGraphQLBodyDTO(
        String query,
        Map<String, Object> variables
) {
}
