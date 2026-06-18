package com.euflausino.requisition.adapter.out.webclient.dto;

import com.euflausino.requisition.adapter.out.webclient.dto.enums.OutRequestBodyTypeDTO;

import java.util.Map;

public record OutRequestModelDTO(
        String name,
        String method,
        String url,
        Map<String, String> headers,
        OutRequestBodyTypeDTO bodyType,
        OutRequestBodyDTO body,
        Map<String, String> queryParams
) {
}
