# Exemplos de Payloads — Tipos de Body

Este documento mostra exemplos completos de payloads para cada tipo de `bodyType` suportado.

## RAW (JSON/Objeto genérico)

O corpo será serializado para JSON. Use quando quiser enviar objetos/estruturas que devem ser convertidas para JSON.

```json
{
  "nome": "Enviar JSON",
  "method": "POST",
  "url": "https://api.example.com/data",
  "headers": {
    "Content-Type": "application/json"
  },
  "bodyType": "RAW",
  "body": {
    "raw": {
      "id": 123,
      "name": "John Doe",
      "active": true,
      "tags": ["user", "admin"]
    }
  }
}
```

**Resultado**: O `raw` é serializado para JSON:
```json
{"id":123,"name":"John Doe","active":true,"tags":["user","admin"]}
```

---

## RAW (String)

Se você enviar uma string em `raw`, ela é enviada **literalmente** sem serialização adicional.

```json
{
  "nome": "Enviar String JSON",
  "method": "POST",
  "url": "https://api.example.com/data",
  "headers": {
    "Content-Type": "application/json"
  },
  "bodyType": "RAW",
  "body": {
    "raw": "{\"message\":\"Hello World\"}"
  }
}
```

**Resultado**: Enviado exatamente como:
```
{"message":"Hello World"}
```

---

## RAW_HTML

Envia HTML/XHTML como texto puro com `Content-Type: text/html`.

```json
{
  "nome": "Enviar HTML",
  "method": "POST",
  "url": "https://example.com/receive-html",
  "headers": {
    "Accept": "text/html"
  },
  "bodyType": "RAW_HTML",
  "body": {
    "raw": "<!DOCTYPE html><html><head><title>Test</title></head><body><h1>Hello World</h1></body></html>"
  }
}
```

**Resultado**: Enviado exatamente como texto HTML, com header `Content-Type: text/html`.

---

## RAW_JAVASCRIPT

Envia JavaScript como texto puro com `Content-Type: application/javascript`.

```json
{
  "nome": "Enviar JavaScript",
  "method": "POST",
  "url": "https://app.example.com/upload-script",
  "bodyType": "RAW_JAVASCRIPT",
  "body": {
    "raw": "function greet(name) {\n  console.log('Hello, ' + name);\n}\ngreet('World');"
  }
}
```

**Resultado**: Enviado exatamente como texto JavaScript, com header `Content-Type: application/javascript`.

---

## RAW_XML

Envia XML como texto puro com `Content-Type: application/xml`.

```json
{
  "nome": "Enviar XML",
  "method": "POST",
  "url": "https://api.example.com/soap",
  "headers": {
    "SOAPAction": "urn:example:action"
  },
  "bodyType": "RAW_XML",
  "body": {
    "raw": "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><user><id>1</id><name>John</name></user></root>"
  }
}
```

**Resultado**: Enviado exatamente como texto XML, com header `Content-Type: application/xml`.

---

## FORM_URLENCODED

Envia dados como `application/x-www-form-urlencoded`.

```json
{
  "nome": "Enviar Form URL Encoded",
  "method": "POST",
  "url": "https://example.com/login",
  "bodyType": "FORM_URLENCODED",
  "body": {
    "formData": {
      "username": "john_doe",
      "password": "secret123",
      "remember": "true"
    }
  }
}
```

**Resultado**:
```
username=john_doe&password=secret123&remember=true
```

Header: `Content-Type: application/x-www-form-urlencoded`

---

## FORM_DATA (Multipart)

Envia dados como `multipart/form-data` (útil para uploading de arquivos).

```json
{
  "nome": "Enviar Form Data",
  "method": "POST",
  "url": "https://upload.example.com/file",
  "bodyType": "FORM_DATA",
  "body": {
    "formData": {
      "name": "John Doe",
      "email": "john@example.com",
      "file": "YmluYXJ5IGRhdGEgaGVyZQ=="
    }
  }
}
```

**Resultado**: Enviado como multipart com boundary separador.

Header: `Content-Type: multipart/form-data; boundary=...`

---

## GRAPHQL

Envia uma query/mutation GraphQL.

```json
{
  "nome": "Query GraphQL",
  "method": "POST",
  "url": "https://api.example.com/graphql",
  "headers": {
    "Authorization": "Bearer token123"
  },
  "bodyType": "GRAPHQL",
  "body": {
    "graphQL": {
      "query": "query { user(id:1) { id name email } }",
      "variables": {
        "id": 1
      }
    }
  }
}
```

**Resultado**: Serializado para JSON:
```json
{"query":"query { user(id:1) { id name email } }","variables":{"id":1}}
```

Header: `Content-Type: application/json`

---

## BINARY

Envia dados binários brutos.

```json
{
  "nome": "Enviar Binary",
  "method": "POST",
  "url": "https://example.com/binary-endpoint",
  "headers": {
    "Content-Type": "application/octet-stream"
  },
  "bodyType": "BINARY",
  "body": {
    "binary": "YmluYXJ5IGRhdGEgaGVyZQ=="
  }
}
```

**Resultado**: byte[] decodificado de base64, enviado como binário puro.

Header: `Content-Type: application/octet-stream` (ou do header personalizado)

---

## NONE

Sem corpo (típico para GET, HEAD, DELETE).

```json
{
  "nome": "GET sem corpo",
  "method": "GET",
  "url": "https://api.example.com/users",
  "headers": {
    "Accept": "application/json"
  },
  "bodyType": "NONE",
  "body": null
}
```

**Resultado**: Requisição sem corpo.

---

## Dicas

1. **RAW com string vs objeto**: Se `body.raw` é uma string, ela é enviada como-é. Se é um objeto, é serializado para JSON.
2. **RAW_HTML, RAW_JAVASCRIPT, RAW_XML**: Enviam conteúdo literal (sem serialização) com o Content-Type apropriado.
3. **Headers customizados**: Sempre pode sobrescrever o `Content-Type` padrão adicionando-o no mapa `headers`.
4. **Query params**: Além do corpo, você pode adicionar `queryParams` para parâmetros de URL.

---

Para mais detalhes, consulte a documentação da API em `docs/API.md`.
