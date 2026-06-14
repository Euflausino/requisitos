package com.euflausino.requisition.dto.mapper;

import com.euflausino.requisition.entity.RespostaEntity;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespostaWebClientMapper {

    public static RespostaEntity mapClientResponseParaRespostaEntity(ClientResponse response, String body, long start, long end) {

        int status = response.statusCode().value();
        String statusText = response.statusCode().toString();
        boolean successful = status >= 200 && status < 300;

        Map<String, List<String>> headers = new HashMap<>();
        response.headers().asHttpHeaders().forEach((k, v) -> headers.put(k, List.copyOf(v)));

        long sizeBody = body != null ? body.getBytes(StandardCharsets.UTF_8).length : 0;
        long sizeHeader = headers.toString().getBytes(StandardCharsets.UTF_8).length;
        long responseTimeMs = end - start;

        return new RespostaEntity(
                status,
                statusText,
                successful,
                headers,
                body,
                responseTimeMs,
                sizeBody,
                sizeHeader
        );
    }

}

