package com.euflausino.requisition.adapter.out.webclient;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.mapper.WebClientMapper;
import com.euflausino.requisition.application.model.FinalRequestBodyModel;

import com.euflausino.requisition.application.model.RequestModel;
import com.euflausino.requisition.application.model.ResponseModel;
import com.euflausino.requisition.adapter.out.webclient.exceptions.NullServerResponseException;
import com.euflausino.requisition.adapter.out.webclient.exceptions.InvalidUrlException;
import com.euflausino.requisition.adapter.out.webclient.preparebody.GenerateBodyFactory;
import com.euflausino.requisition.application.port.out.MakesRequestOut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;

@Service
public class SendWebClientRequest implements MakesRequestOut {

    private final WebClient webClient;
    private final GenerateBodyFactory generateBodyFactory;

    public SendWebClientRequest(
            WebClient.Builder webClientBuilder,  GenerateBodyFactory generateBodyFactory) {
        this.webClient = webClientBuilder.build();
        this.generateBodyFactory = generateBodyFactory;

    }

    @Override
    public ResponseModel send(RequestModel requestModel) {

        OutRequestModelDTO request = WebClientMapper.mapRequestModelToDTO(requestModel);
        validateRequest(request);

        URI uri = uriBuilder(request);
        OutCompletedRequestBodyDTO generatedBody = generateBodyFactory.generateBody(request.bodyType(),request);

        long startRequestTime = System.currentTimeMillis();
        Map.Entry<ClientResponse, String> webClientResponse = executeRequest(request, uri, generatedBody);
        long endRequestTime = System.currentTimeMillis();

        return mapResponse(webClientResponse, startRequestTime, endRequestTime);
    }

    private void validateRequest(OutRequestModelDTO request) {
        if (request == null || request.url() == null || request.url().isEmpty()) {
            throw new InvalidUrlException("URL is missing!");
        }
    }

    private URI uriBuilder(OutRequestModelDTO request) {
        String url = request.url();

        if (request.queryParams() == null || request.queryParams().isEmpty()) {
            return URI.create(url);
        }
        return URI.create(url + uriAndQueryBuilder(request.queryParams()));
    }

    private String uriAndQueryBuilder(Map<String, String> params) {
        StringBuilder sb = new StringBuilder("?");
        params.forEach((key, value) -> {
            if (sb.length() > 1) sb.append("&");
            sb.append(key).append("=").append(value);
        });
        return sb.toString();
    }

    private Map.Entry<ClientResponse, String> executeRequest(
            OutRequestModelDTO request,
            URI uri,
            OutCompletedRequestBodyDTO body) {

        WebClient.RequestBodySpec requestSpec = startRequest(request, uri);
        addHeaders(requestSpec, request, body);
        WebClient.RequestHeadersSpec<?> headersSpec = attachBody(requestSpec, body);

        return catchResponseWithBody(headersSpec);
    }

    private WebClient.RequestBodySpec startRequest(OutRequestModelDTO request, URI uri) {
        return webClient
                .method(HttpMethod.valueOf(request.method().toUpperCase()))
                .uri(uri);
    }

    private void addHeaders(
            WebClient.RequestBodySpec spec,
            OutRequestModelDTO request,
            OutCompletedRequestBodyDTO body) {

        if (request.headers() != null) {
            request.headers().forEach(spec::header);
        }

        if (body.contentType() != null &&
            (request.headers() == null || !request.headers().containsKey("Content-Type"))) {
            spec.header(HttpHeaders.CONTENT_TYPE, body.contentType());
        }
    }

    private WebClient.RequestHeadersSpec<?> attachBody(
            WebClient.RequestBodySpec spec,
            OutCompletedRequestBodyDTO body) {

        if (body.data() == null) {
            return spec.body(BodyInserters.empty());
        }

        if (body.data() instanceof MultiValueMap) {
            return spec.body(BodyInserters.fromMultipartData((MultiValueMap<String, ?>) body.data()));
        }

        return spec.body(BodyInserters.fromValue(body.data()));
    }

    private Map.Entry<ClientResponse, String> catchResponseWithBody(WebClient.RequestHeadersSpec<?> spec) {
        return spec
            .exchangeToMono(this::readResponseWithBody)
            .block();
    }

    private Mono<? extends Map.Entry<ClientResponse, String>> readResponseWithBody(ClientResponse response) {
        return response.bodyToMono(String.class)
            .switchIfEmpty(Mono.just(""))
            .map(body -> new AbstractMap.SimpleEntry<>(response, body))
            .onErrorResume(error -> Mono.just(new AbstractMap.SimpleEntry<>(response, "")));
    }

    private ResponseModel mapResponse(
            Map.Entry<ClientResponse, String> webClientResponse,
            long startRequestTime,
            long endRequestTime) {

        if (webClientResponse == null) {
            throw new NullServerResponseException("Null server response!");
        }

        ClientResponse response = webClientResponse.getKey();
        String body = webClientResponse.getValue();

        return WebClientMapper.mapClientResponseToResponseModel(
                response, body, startRequestTime, endRequestTime);
    }
}
