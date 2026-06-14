package com.euflausino.requisition.service;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.dto.mapper.RespostaWebClientMapper;
import com.euflausino.requisition.entity.RequisicaoEntity;
import com.euflausino.requisition.entity.RespostaEntity;
import com.euflausino.requisition.exceptions.RespostaNulaDoServidorException;
import com.euflausino.requisition.exceptions.UrlInvalidaException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class EnviaRequisicaoEspecificadaWebClientService {

    private final WebClient webClient;
    private final GerenciaCorpoDaRequisicaoService gerenciadorCorpo;

    public EnviaRequisicaoEspecificadaWebClientService(
            WebClient.Builder webClientBuilder,
            GerenciaCorpoDaRequisicaoService gerenciadorCorpo) {
        this.webClient = webClientBuilder.build();
        this.gerenciadorCorpo = gerenciadorCorpo;
    }

    public RespostaEntity enviar(RequisicaoEntity requisicao) throws JsonProcessingException {
        validarRequisicao(requisicao);

        URI uri = construirUri(requisicao);
        CorpoRequisicaoPreparadoEntity corpoPreparado = gerenciadorCorpo.prepararCorpo(requisicao);

        long tempoInicio = System.currentTimeMillis();
        Map.Entry<ClientResponse, String> respostaBruta = executarRequisicao(requisicao, uri, corpoPreparado);
        long tempoFinal = System.currentTimeMillis();

        return mapearResposta(respostaBruta, tempoInicio, tempoFinal);
    }

    private void validarRequisicao(RequisicaoEntity requisicao) {
        if (requisicao == null || requisicao.getUrl() == null || requisicao.getUrl().isEmpty()) {
            throw new UrlInvalidaException("URL é obrigatória");
        }
    }

    private URI construirUri(RequisicaoEntity requisicao) {
        String url = requisicao.getUrl();

        if (requisicao.getQueryParams() == null || requisicao.getQueryParams().isEmpty()) {
            return URI.create(url);
        }

        return URI.create(url + construirQueryString(requisicao.getQueryParams()));
    }

    private String construirQueryString(Map<String, String> parametros) {
        StringBuilder sb = new StringBuilder("?");
        parametros.forEach((chave, valor) -> {
            if (sb.length() > 1) sb.append("&");
            sb.append(chave).append("=").append(valor);
        });
        return sb.toString();
    }

    private Map.Entry<ClientResponse, String> executarRequisicao(
            RequisicaoEntity requisicao,
            URI uri,
            CorpoRequisicaoPreparadoEntity corpo) {

        WebClient.RequestBodySpec requestSpec = iniciarRequisicao(requisicao, uri);
        adicionarHeaders(requestSpec, requisicao, corpo);
        WebClient.RequestHeadersSpec<?> headersSpec = anexarCorpo(requestSpec, corpo);

        return capturarRespostaComCorpo(headersSpec);
    }

    private WebClient.RequestBodySpec iniciarRequisicao(RequisicaoEntity requisicao, URI uri) {
        return webClient
                .method(HttpMethod.valueOf(requisicao.getMethod().toUpperCase()))
                .uri(uri);
    }

    private void adicionarHeaders(
            WebClient.RequestBodySpec spec,
            RequisicaoEntity requisicao,
            CorpoRequisicaoPreparadoEntity corpo) {

        if (requisicao.getHeaders() != null) {
            requisicao.getHeaders().forEach(spec::header);
        }

        if (corpo.getTipoConteudo() != null &&
            (requisicao.getHeaders() == null || !requisicao.getHeaders().containsKey("Content-Type"))) {
            spec.header(HttpHeaders.CONTENT_TYPE, corpo.getTipoConteudo());
        }
    }

    private WebClient.RequestHeadersSpec<?> anexarCorpo(
            WebClient.RequestBodySpec spec,
            CorpoRequisicaoPreparadoEntity corpo) {

        if (!corpo.temDados()) {
            return spec.body(BodyInserters.empty());
        }

        if (corpo.getDados() instanceof MultiValueMap) {
            return spec.body(BodyInserters.fromMultipartData((MultiValueMap<String, ?>) corpo.getDados()));
        }

        return spec.body(BodyInserters.fromValue(corpo.getDados()));
    }

    private Map.Entry<ClientResponse, String> capturarRespostaComCorpo(WebClient.RequestHeadersSpec<?> spec) {
        return spec
            .exchangeToMono(this::lerRespostaComCorpo)
            .block();
    }

    private Mono<? extends Map.Entry<ClientResponse, String>> lerRespostaComCorpo(ClientResponse resposta) {
        return resposta.bodyToMono(String.class)
            .switchIfEmpty(Mono.just(""))
            .map(corpo -> new AbstractMap.SimpleEntry<>(resposta, corpo))
            .onErrorResume(erro -> Mono.just(new AbstractMap.SimpleEntry<>(resposta, "")));
    }

    private RespostaEntity mapearResposta(
            Map.Entry<ClientResponse, String> respostaBruta,
            long tempoInicio,
            long tempoFinal) {

        if (respostaBruta == null) {
            throw new RespostaNulaDoServidorException("Resposta nula do servidor");
        }

        ClientResponse resposta = respostaBruta.getKey();
        String corpo = respostaBruta.getValue();

        return RespostaWebClientMapper.mapClientResponseParaRespostaEntity(
            resposta, corpo, tempoInicio, tempoFinal);
    }
}
