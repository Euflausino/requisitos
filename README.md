# Requisition

Projeto Spring Boot para enviar requisições HTTP configuráveis e armazená-las.

Este documento descreve como compilar, executar e usar rapidamente a API, além de explicar os principais componentes do código e exemplos de payloads.

## Sumário

- Visão geral
- Requisitos
- Compilar e executar
- Endpoints REST
- Modelos (DTOs)
- Exemplos de requisições (curl)
- Documentação completa
- Configuração de keep-alive / Connection pooling
- Como rodar testes
- Estrutura do projeto
- Como contribuir

## Visão geral

O serviço recebe um payload que descreve uma requisição (método, URL, headers, body, query params), executa essa requisição contra o destino especificado usando `WebClient` e devolve um objeto de resposta contendo código, headers, body e tempos.

Existem duas implementações (históricas) para envio de requisições:
- `EnviaRequisicaoEspecificadaService` — implementação com OkHttp (mantida para compatibilidade)
- `EnviaRequisicaoEspecificadaWebClientService` — implementação atual com `WebClient` (bloqueia para manter API síncrona)

A lógica de construção do corpo foi centralizada em `GerenciaCorpoDaRequisicaoService` que retorna um DTO (`CorpoRequisicaoPreparado`) com o `dados` e `tipoConteudo` prontos para serem inseridos no request.

## Requisitos

- JDK 21
- Maven (o wrapper `./mvnw` já está incluído)

## Compilar e executar

Compilar:

```bash
./mvnw -DskipTests package
```

Executar (jar gerado):

```bash
java -jar target/Requisition-0.0.1-SNAPSHOT.jar
```

Ou executar direto pelo Maven (modo dev):

```bash
./mvnw spring-boot:run
```

A aplicação por padrão roda em `http://localhost:8080` (confirme em `src/main/resources/application.properties`).

## Endpoints REST

Base: `/requisicao`

- POST `/requisicao/enviar` — Recebe um `DTORequisicao`, executa a requisição descrita e retorna `DTOResposta`.
- POST `/requisicao/salvar` — Persiste a requisição (retorna a requisição salva). 

### Exemplo: enviar requisição

POST /requisicao/enviar

Payload mínimo:

```json
{
  "nome": "Teste GET com body",
  "method": "GET",
  "url": "http://localhost:8081/email/health",
  "headers": {
    "Accept": "text/plain"
  },
  "bodyType": "RAW",
  "body": {
    "raw": { "any":"value" }
  }
}
```

Observações:
- O projeto permite enviar GET com body (não obrigatório, mas suportado)
- Se usar `bodyType: RAW`, o campo `body.raw` pode ser qualquer objeto — será serializado para JSON
- Para `FORM_DATA`/`FORM_URLENCODED` use `body.formData` com um objeto de chave/valor
- Para `GRAPHQL` coloque o campo `graphQL` dentro de `body`
- **Novo:** Use `RAW_HTML`, `RAW_JAVASCRIPT` ou `RAW_XML` para enviar conteúdo literal (não serializado) — ver exemplos abaixo

### Exemplos com curl

Enviar GET com body (exemplo):

```bash
curl -X POST http://localhost:8080/requisicao/enviar \
  -H "Content-Type: application/json" \
  -d '{
    "nome":"Teste GET com body",
    "method":"GET",
    "url":"http://localhost:8081/email/health",
    "headers":{"Accept":"text/plain"},
    "bodyType":"RAW",
    "body":{"raw": {"ping":"ping"}}
  }'
```

Enviar HTML (RAW_HTML):

```bash
curl -X POST http://localhost:8080/requisicao/enviar \
  -H "Content-Type: application/json" \
  -d '{
    "nome":"Enviar HTML",
    "method":"POST",
    "url":"http://example.com/receive",
    "bodyType":"RAW_HTML",
    "body":{"raw":"<html><body><h1>Hello</h1></body></html>"}
  }'
```

Enviar JavaScript (RAW_JAVASCRIPT):

```bash
curl -X POST http://localhost:8080/requisicao/enviar \
  -H "Content-Type: application/json" \
  -d '{
    "nome":"Enviar JavaScript",
    "method":"POST",
    "url":"http://example.com/script",
    "bodyType":"RAW_JAVASCRIPT",
    "body":{"raw":"function hello() { console.log(\"Hello\"); }"}
  }'
```

Enviar XML (RAW_XML):

```bash
curl -X POST http://localhost:8080/requisicao/enviar \
  -H "Content-Type: application/json" \
  -d '{
    "nome":"Enviar XML",
    "method":"POST",
    "url":"http://example.com/xml",
    "bodyType":"RAW_XML",
    "body":{"raw":"<?xml version=\"1.0\"?><root><item>value</item></root>"}
  }'
```

Salvar requisição:

```bash
curl -X POST http://localhost:8080/requisicao/salvar \
  -H "Content-Type: application/json" \
  -d '{ /* mesmo payload do DTORequisicao */ }'
```

## Modelos (DTOs)

Principais DTOs usados pela API (sintaxe simplificada):

- DTORequisicao
  - nome: String
  - method: String (GET, POST, PUT, DELETE, ...)
  - url: String
  - headers: Map<String,String>
  - bodyType: DTOTipoRequisicao (NONE, FORM_DATA, FORM_URLENCODED, RAW, BINARY, GRAPHQL)
  - body: DTOConteudoRequisicao
  - queryParams: Map<String,String>

- DTOConteudoRequisicao
  - raw: Object
  - formData: Map<String,Object>
  - binary: byte[] (base64 se usado via JSON)
  - graphQL: GraphQLBodyEntity

- DTOResposta (retorno de `/enviar`)
  - status: Integer
  - statusText: String
  - successful: Boolean
  - headers: Map<String,List<String>>
  - body: String
  - responseTimeMs: Long
  - bodySizeBytes: Long
  - headerSizeByte: Long

Consulte `src/main/java/com/euflausino/requisition/dto` para os registros (records) completos.

## Estrutura do projeto

Principais pacotes:
- `com.euflausino.requisition.controller` — controllers REST
- `com.euflausino.requisition.dto` — DTOs públicos da API
- `com.euflausino.requisition.send.service` — serviços que constroem e enviam requisições
- `com.euflausino.requisition.send.dto.mapper` — mappers de resposta

## Documentação completa

- **[docs/API.md](docs/API.md)** — Referência detalhada de endpoints, DTOs e tipos suportados
- **[docs/EXAMPLES.md](docs/EXAMPLES.md)** — Exemplos de payloads para cada tipo de body
- **[docs/TRANSMISSION_EXAMPLES.md](docs/TRANSMISSION_EXAMPLES.md)** — Como o conteúdo é transmitido na rede para cada tipo
- **[docs/CHANGELOG.md](docs/CHANGELOG.md)** — Histórico de mudanças

### Entendimento rápido
Se você quer entender a **diferença prática** entre `RAW`, `RAW_HTML`, `RAW_JAVASCRIPT` e `RAW_XML`, veja [docs/TRANSMISSION_EXAMPLES.md](docs/TRANSMISSION_EXAMPLES.md) — mostra exatamente o que é enviado na rede em cada caso.

## Testes

Para rodar os testes (se existirem):

```bash
./mvnw test
```

## Contribuição

1. Abra uma issue descrevendo o problema/feature
2. Crie uma branch nomeada `feature/<nome>` ou `fix/<nome>`
3. Faça PR com descrição clara e testes quando aplicável

