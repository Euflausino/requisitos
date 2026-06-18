package com.euflausino.requisition.adapter.in.dto;

import com.euflausino.requisition.adapter.in.dto.enums.RequestBodyTypeDTO;

import java.util.Map;

public record RequestDTO(

        String name,
        String method,
        String url,
        Map<String, String>headers,
        RequestBodyTypeDTO bodyType,
        RequestBodyDTO body,
        Map<String, String> queryParams
) {
}
