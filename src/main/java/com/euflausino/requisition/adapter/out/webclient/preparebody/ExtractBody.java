package com.euflausino.requisition.adapter.out.webclient.preparebody;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExtractBody {
    public static String extract(Map<String, String> headers, String defaultContentType) {
        if (headers != null) {
            return headers.getOrDefault("Content-Type", defaultContentType);
        }
        return defaultContentType;
    }
}
