package com.euflausino.requisition.config;

import com.euflausino.requisition.application.port.out.MakesRequestOut;
import com.euflausino.requisition.application.port.out.SaveCollectionOut;
import com.euflausino.requisition.application.port.out.SaveRequestOut;
import com.euflausino.requisition.application.usecase.SaveCollectionUseCase;
import com.euflausino.requisition.application.usecase.RequestUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public SaveCollectionUseCase saveCollectionUseCase(SaveCollectionOut saveCollectionOut) {
        return new SaveCollectionUseCase(saveCollectionOut);
    }

    @Bean
    public RequestUseCase saveRequestUseCase(SaveRequestOut saveRequestOut, MakesRequestOut makesRequest) {
        return new RequestUseCase(saveRequestOut, makesRequest);
    }
}
