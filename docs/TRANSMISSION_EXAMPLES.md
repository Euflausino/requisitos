# Como o conteúdo é enviado — Exemplos práticos

Este documento mostra exatamente o que é enviado na rede para cada tipo de body, comparando a transmissão HTTP real.

## Resumo rápido

| Tipo | `body.raw` | O que é enviado | Content-Type |
|------|-----------|-----------------|--------------|
| **RAW** (objeto) | `{...}` | Serializado para JSON | application/json |
| **RAW** (string) | `"string"` | String literal (sem escaping) | application/json |
| **RAW_HTML** | `"<html>..."` | String literal (HTML puro) | text/html |
| **RAW_JAVASCRIPT** | `"function..."` | String literal (JS puro) | application/javascript |
| **RAW_XML** | `"<?xml>..."` | String literal (XML puro) | application/xml |

---

## Exemplo 1: RAW com Objeto

### Payload enviado para a API

```json
{
  "nome": "Criar usuário",
  "method": "POST",
  "url": "http://api.example.com/users",
  "bodyType": "RAW",
  "body": {
    "raw": {
      "id": 123,
      "name": "John Doe",
      "email": "john@example.com",
      "active": true
    }
  }
}
```

### O que é transmitido na rede (HTTP)

```http
POST /users HTTP/1.1
Host: api.example.com
Content-Type: application/json
Content-Length: 81

{"id":123,"name":"John Doe","email":"john@example.com","active":true}
```

### Como o servidor recebe

```javascript
// Node.js/Express
app.post('/users', (req, res) => {
  console.log(req.body);
  // Saída: { id: 123, name: 'John Doe', email: 'john@example.com', active: true }
});
```

**Importante**: O objeto `{ id: 123, ... }` foi **serializado para JSON string** e enviado.

---

## Exemplo 2: RAW com String JSON

### Payload enviado para a API

```json
{
  "nome": "Criar usuário",
  "method": "POST",
  "url": "http://api.example.com/users",
  "bodyType": "RAW",
  "body": {
    "raw": "{\"id\":123,\"name\":\"John Doe\",\"email\":\"john@example.com\",\"active\":true}"
  }
}
```

### O que é transmitido na rede (HTTP)

```http
POST /users HTTP/1.1
Host: api.example.com
Content-Type: application/json
Content-Length: 81

{"id":123,"name":"John Doe","email":"john@example.com","active":true}
```

### Como o servidor recebe

```javascript
app.post('/users', (req, res) => {
  console.log(req.body);
  // Saída: { id: 123, name: 'John Doe', email: 'john@example.com', active: true }
});
```

**Importante**: Mesmo sendo string JSON no payload da API, é enviada **exatamente igual** ao exemplo anterior. A string não é escapada novamente.

---

## Exemplo 3: RAW_HTML

### Payload enviado para a API

```json
{
  "nome": "Enviar página",
  "method": "POST",
  "url": "http://example.com/store-page",
  "bodyType": "RAW_HTML",
  "body": {
    "raw": "<!DOCTYPE html><html><head><title>Test</title></head><body><h1>Hello World</h1></body></html>"
  }
}
```

### O que é transmitido na rede (HTTP)

```http
POST /store-page HTTP/1.1
Host: example.com
Content-Type: text/html
Content-Length: 93

<!DOCTYPE html><html><head><title>Test</title></head><body><h1>Hello World</h1></body></html>
```

### Como o servidor recebe

```javascript
app.post('/store-page', (req, res) => {
  console.log(req.get('content-type')); // "text/html"
  console.log(body);
  // Saída (texto puro):
  // <!DOCTYPE html><html><head><title>Test</title></head><body><h1>Hello World</h1></body></html>
});
```

**Diferença crítica**: O conteúdo é enviado como **texto HTML puro**, não como JSON. O servidor não tenta fazer parse JSON — recebe uma string literal.

---

## Exemplo 4: RAW_JAVASCRIPT

### Payload enviado para a API

```json
{
  "nome": "Enviar script",
  "method": "POST",
  "url": "http://cdn.example.com/upload-script",
  "bodyType": "RAW_JAVASCRIPT",
  "body": {
    "raw": "function greet(name) {\n  console.log('Hello, ' + name);\n}\ngreet('World');"
  }
}
```

### O que é transmitido na rede (HTTP)

```http
POST /upload-script HTTP/1.1
Host: cdn.example.com
Content-Type: application/javascript
Content-Length: 78

function greet(name) {
  console.log('Hello, ' + name);
}
greet('World');
```

### Como o servidor recebe

```javascript
app.post('/upload-script', (req, res) => {
  console.log(req.get('content-type')); // "application/javascript"
  console.log(body);
  // Saída (texto puro):
  // function greet(name) {
  //   console.log('Hello, ' + name);
  // }
  // greet('World');
});
```

**Diferença crítica**: Código JavaScript **literal**, pronto para ser executado ou armazenado como arquivo `.js`.

---

## Exemplo 5: RAW_XML

### Payload enviado para a API

```json
{
  "nome": "Enviar XML",
  "method": "POST",
  "url": "http://soap.example.com/service",
  "bodyType": "RAW_XML",
  "body": {
    "raw": "<?xml version=\"1.0\" encoding=\"UTF-8\"?><users><user><id>123</id><name>John</name></user></users>"
  }
}
```

### O que é transmitido na rede (HTTP)

```http
POST /service HTTP/1.1
Host: soap.example.com
Content-Type: application/xml
Content-Length: 110

<?xml version="1.0" encoding="UTF-8"?><users><user><id>123</id><name>John</name></user></users>
```

### Como o servidor recebe

```javascript
app.post('/service', (req, res) => {
  console.log(req.get('content-type')); // "application/xml"
  console.log(body);
  // Saída (texto puro):
  // <?xml version="1.0" encoding="UTF-8"?><users><user><id>123</id><name>John</name></user></users>
});
```

**Diferença crítica**: XML **puro**, não JSON. O servidor espera um parser XML, não JSON.

---

## Comparação lado a lado

### Lado do cliente (seu payload JSON para a API)

```json
// RAW com objeto JSON
{ "bodyType": "RAW", "body": { "raw": { "id": 1 } } }

// RAW com string JSON
{ "bodyType": "RAW", "body": { "raw": "{\"id\":1}" } }

// RAW_HTML
{ "bodyType": "RAW_HTML", "body": { "raw": "<html>...</html>" } }

// RAW_JAVASCRIPT
{ "bodyType": "RAW_JAVASCRIPT", "body": { "raw": "function() { ... }" } }

// RAW_XML
{ "bodyType": "RAW_XML", "body": { "raw": "<?xml>...</xml>" } }
```

### O que chega no servidor (HTTP body)

```
RAW (objeto):         {"id":1}
RAW (string):         {"id":1}
RAW_HTML:             <html>...</html>
RAW_JAVASCRIPT:       function() { ... }
RAW_XML:              <?xml>...</xml>
```

---

## Fluxo passo-a-passo para cada tipo

### RAW com Objeto

```
1. Cliente envia JSON:
   { "bodyType": "RAW", "body": { "raw": { "id": 1 } } }

2. GerenciaCorpoDaRequisicaoService:
   - Detecta que raw é um OBJETO
   - Chama objectMapper.writeValueAsString(raw)
   - Resultado: string '{"id":1}'

3. WebClient envia:
   - Body: {"id":1}
   - Content-Type: application/json

4. Servidor recebe:
   - Body como texto: {"id":1}
   - Parser JSON decodifica para { id: 1 }
```

### RAW com String

```
1. Cliente envia JSON:
   { "bodyType": "RAW", "body": { "raw": "{\"id\":1}" } }

2. GerenciaCorpoDaRequisicaoService:
   - Detecta que raw é uma STRING
   - NÃO serializa (já é string!)
   - Resultado: string '{"id":1}' (enviada como-é)

3. WebClient envia:
   - Body: {"id":1}
   - Content-Type: application/json

4. Servidor recebe:
   - Body como texto: {"id":1}
   - Parser JSON decodifica para { id: 1 }
```

### RAW_HTML

```
1. Cliente envia JSON:
   { "bodyType": "RAW_HTML", "body": { "raw": "<html>Hello</html>" } }

2. GerenciaCorpoDaRequisicaoService:
   - Detecta tipo RAW_HTML
   - Envia raw como string literal (sem serialização)
   - Content-Type definido como: text/html

3. WebClient envia:
   - Body: <html>Hello</html>
   - Content-Type: text/html

4. Servidor recebe:
   - Body como texto puro: <html>Hello</html>
   - NÃO tenta parse JSON
   - Trata como HTML (armazena, renderiza, etc)
```

---

## Casos de uso reais

### Caso 1: Enviar JSON para API REST

Use **RAW com objeto**:

```json
{
  "bodyType": "RAW",
  "body": { "raw": { "username": "john", "password": "secret" } }
}
```

→ Enviado como JSON, servidor faz parse JSON.

---

### Caso 2: Enviar HTML para servidor de armazenamento

Use **RAW_HTML**:

```json
{
  "bodyType": "RAW_HTML",
  "body": { "raw": "<html><body>Page</body></html>" }
}
```

→ Enviado como HTML puro, servidor armazena como arquivo `.html`.

---

### Caso 3: Enviar GraphQL Query pré-pronta como string

Use **RAW (com string JSON)**:

```json
{
  "bodyType": "RAW",
  "body": { "raw": "{\"query\":\"{ users { id name } }\"}" }
}
```

→ Enviado como JSON, servidor parseia.

Ou use **GRAPHQL** (mais explícito):

```json
{
  "bodyType": "GRAPHQL",
  "body": { "graphQL": { "query": "{ users { id name } }" } }
}
```

→ Automatically serialized to JSON.

---

### Caso 4: Enviar JavaScript para compilador online

Use **RAW_JAVASCRIPT**:

```json
{
  "bodyType": "RAW_JAVASCRIPT",
  "body": { "raw": "console.log('Hello');" }
}
```

→ Enviado como JS puro, servidor compila/minifica/executa.

---

## Resumo prático

- **Use RAW para JSON/APIs**: O objeto é serializado automaticamente
- **Use RAW com string JSON**: Quando você já tem JSON pronto como string
- **Use RAW_HTML para HTML**: Quando você quer enviar HTML puro (não como JSON dentro de um campo)
- **Use RAW_JAVASCRIPT para JS**: Quando você quer enviar código JavaScript literal
- **Use RAW_XML para XML**: Quando você quer enviar XML puro (SOAP, etc)

A diferença prática é o **Content-Type** e se o conteúdo é **literalmente** o que aparece no body HTTP ou se é **serializado** para JSON primeiro.

