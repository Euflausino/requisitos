# API Reference — Requisition

Documentação das rotas e dos principais tipos usados pela API.

## Endpoints

### POST /requisicao/enviar

Descrição: executa a requisição descrita no corpo e retorna o resultado.

Request: `DTORequisicao` (JSON)

Response: `DTOResposta` (JSON)

Exemplo de request (GET com body):

```json
{
  "nome":"Teste GET com body",
  "method":"GET",
  "url":"http://example.local/endpoint",
  "headers":{"Accept":"application/json"},
  "bodyType":"RAW",
  "body":{ "raw": {"ping":"pong"} }
}
```

### POST /requisicao/salvar

Descrição: persiste a requisição recebida (salvamento local/dados) e retorna a entidade salva.

Request: `DTORequisicao` (JSON)

Response: `DTORequisicao` (JSON)

---

## DTOs (resumo)

### DTORequisicao
- nome: String — rótulo da requisição
- method: String — verbo HTTP (GET, POST, PUT, ...)
- url: String — URL alvo
- headers: Map<String,String> — headers a serem enviados
- bodyType: DTOTipoRequisicao — tipo do corpo
- body: DTOConteudoRequisicao — conteúdo específico
- queryParams: Map<String,String> — parâmetros de query

### DTOTipoRequisicao
Valores:
- NONE
- FORM_DATA
- FORM_URLENCODED
- RAW
- BINARY
- GRAPHQL

### DTOConteudoRequisicao
- raw: Object — conteúdo bruto (serializado para JSON quando RAW)
- formData: Map<String,Object> — pares para form-data/urlencoded
- binary: byte[] — dado binário (use base64 quando serializar em JSON)
- graphQL: GraphQLBodyEntity — estrutura usada para GraphQL

### DTOResposta
- status: Integer
- statusText: String
- successful: Boolean
- headers: Map<String,List<String>>
- body: String
- responseTimeMs: Long
- bodySizeBytes: Long
- headerSizeByte: Long
