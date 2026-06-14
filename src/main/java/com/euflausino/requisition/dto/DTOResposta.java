package com.euflausino.requisition.dto;

import java.util.List;
import java.util.Map;

public record DTOResposta(
        Integer status,
        String statusText,
        Boolean successful,
        Map<String, List<String>>headers,
        String body,
        Long responseTimeMs,
        Long bodySizeBytes,
        Long headerSizeByte
) {
}
