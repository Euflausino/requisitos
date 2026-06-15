# CHANGELOG

Histórico de mudanças do projeto Requisition.

## [Versão 0.0.2] — em desenvolvimento

### Adicionado
- Novos tipos de body para envio de conteúdo literal:
  - `RAW_HTML` — Envia HTML como texto puro (não serializado)
  - `RAW_JAVASCRIPT` — Envia JavaScript como texto puro
  - `RAW_XML` — Envia XML como texto puro
- Documentação completa em formato Markdown:
  - `README.md` — Guia rápido e visão geral
  - `docs/API.md` — Referência detalhada de endpoints e DTOs
  - `docs/EXAMPLES.md` — Exemplos de payloads para cada tipo de body
  - `docs/CHANGELOG.md` — Este arquivo

### Alterado
- `GerenciaCorpoDaRequisicaoService.prepararBodyRaw()` agora detecta strings e as envia literalmente, sem serialização adicional
- Adicionado método `prepararBodyLiteral()` para reutilizar lógica de envio de conteúdo não-serializado

### Melhorias
- Melhor tratamento de tipos de conteúdo com Content-Type apropriado para cada tipo
- Suporte aprimorado para o tipo `RAW` com string — se `body.raw` for string, é enviada literal; se for objeto, é serializada para JSON

## [Versão 0.0.1] — Release inicial

### Adicionado
- Projeto base Spring Boot 4.1.0 com Java 21
- API REST para envio de requisições HTTP configuráveis
- Suporte para múltiplos tipos de body:
  - `NONE` — Sem corpo
  - `RAW` — JSON/objeto genérico
  - `FORM_DATA` — Multipart form-data
  - `FORM_URLENCODED` — Application/x-www-form-urlencoded
  - `BINARY` — Dados binários
  - `GRAPHQL` — Queries/mutations GraphQL
- Implementação usando `WebClient` (Spring WebFlux)
- Suporte para GET com body (não obrigatório)
- Endpoints:
  - `POST /requisicao/enviar` — Executa requisição e retorna resposta
  - `POST /requisicao/salvar` — Persiste requisição localmente
- Mappers para conversão entre DTOs e entidades
- Tratamento de headers customizados, incluindo `Connection` e `Keep-Alive`
- Medição de tempo de resposta e tamanho do corpo

---

Veja `docs/API.md` para documentação da API e `docs/EXAMPLES.md` para exemplos de uso.
