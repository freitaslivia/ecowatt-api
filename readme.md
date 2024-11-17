# Resumo sobre a EcoWatt
A EcoWatt é uma solução inovadora de automação e monitoramento energético que
transforma residências em ambientes inteligentes, sustentáveis e econômicos. Com
uma central de controle conectada a sensores distribuídos por toda a casa, a EcoWatt
monitora e gerencia o consumo de energia em tempo real, priorizando o uso de fontes
renováveis como solar e eólica. O sistema armazena a energia gerada, analisa as
condições climáticas para prever a produção e alterna automaticamente para a rede
elétrica quando necessário. Através de um aplicativo intuitivo, o usuário pode visualizar
o consumo detalhado de cada dispositivo, configurar preferências de uso e acessar
relatórios completos que ajudam a economizar e otimizar o uso de energia.

Combinando conveniência e sustentabilidade, a EcoWatt vai além do simples
monitoramento. Nosso sistema inclui um módulo exclusivo para carregamento veicular,
garantindo que veículos elétricos sejam carregados de forma eficiente e sustentável.
Nossa missão é oferecer uma solução prática que reduz os custos e o impacto
ambiental, preparando residências para os desafios energéticos do futuro. A EcoWatt é
mais do que um produto: é uma ferramenta poderosa para quem deseja ter controle
total sobre o consumo de energia, contribuindo para um mundo mais sustentável


# EcoWatt – Transforme sua Casa em um Ambiente

Inteligente e Sustentável
Bem-vindo à EcoWatt! Em um cenário onde o consumo de energia cresce, os custos
aumentam e a sustentabilidade é uma necessidade urgente, a EcoWatt traz uma
solução inovadora para o futuro da energia residencial. Imagine uma casa capaz de
gerar e gerenciar sua própria energia de forma inteligente e econômica – essa é a visão
da EcoWatt.


- O Que é a EcoWatt?
  A EcoWatt é uma solução completa de automação e monitoramento energético que
  transforma sua residência em um ambiente inteligente e eficiente. Com um sistema
  integrado que monitora, gerencia e otimiza o uso de fontes de energia – solar, eólica e
  rede elétrica convencional – a EcoWatt conecta sua casa a uma central de controle que
  prioriza o uso de energias renováveis, reduzindo a dependência da rede elétrica e
  trazendo economia para você.


- Funcionalidades e Diferenciais
  A EcoWatt vai além do monitoramento básico. Nosso sistema armazena a energia
  gerada e usa previsões climáticas para otimizar o uso das fontes renováveis. Quando
  necessário, ele alterna automaticamente para a rede elétrica, garantindo um
  fornecimento contínuo sem necessidade de intervenção do usuário. Tudo isso é
  facilmente acessado através de um aplicativo intuitivo, onde você acompanha o
  consumo em tempo real, configura prioridades de energia e visualiza relatórios
  detalhados. Para proprietários de veículos elétricos, a EcoWatt inclui um módulo de
  carregamento sustentável e eficiente.
  Com a EcoWatt, você assume o controle total sobre o consumo de energia da sua casa,
  economiza e contribui para um futuro mais sustentável.


- Junte-se à Revolução Energética
  Na EcoWatt, nossa missão é promover a inovação, a sustentabilidade e a qualidade de
  vida. Oferecemos uma solução prática para os desafios energéticos atuais, preparando
  sua residência para o futuro. Transforme sua casa em um ambiente inteligente e
  sustentável com a EcoWatt. Acesse nossa landing page para saber mais e descubra
  como podemos ajudar você a economizar e a preservar o meio ambiente.

EcoWatt – juntos, construindo um futuro mais sustentável.



# Documentação ECOWATT API REST


> SQL:

```sql
   create table ecowatt_sensores (
        id_sensor INT(11) not null auto_increment,
        data_criacao datetime(6) not null,
        data_modificacao datetime(6),
        ds_descricao VARCHAR(500),
        ds_local_instalacao VARCHAR(100),
        ds_nome_sensor VARCHAR(100) not null,
        ds_produto_conectado VARCHAR(100) not null,
        nr_sinal INT(11),
        ds_status VARCHAR(20),
        ds_tipo_sensor VARCHAR(50) not null,
        id_usuario INT(11) not null,
        primary key (id_sensor)
    )
    create table ecowatt_usuario (
        id_usuario INT(11) not null auto_increment,
        data_criacao datetime(6) not null,
        data_modificacao datetime(6),
        ds_usuario VARCHAR(150) not null,
        ds_nome_completo VARCHAR(250) not null,
        ds_senha VARCHAR(250) not null,
        primary key (id_usuario)
    ) 
    alter table ecowatt_sensores 
       add constraint FKs75840mxhqu6ekb2jkam4bqob 
       foreign key (id_usuario) 
       references ecowatt_usuario (id_usuario) 
       on delete cascade
```

## Intruções
Para acessar os endpoints que exigem autenticação, é necessário primeiro cadastrar um usuário no banco de dados (caso ainda não tenha um cadastro). Para isso, consulte a seção "Cadastro de Usuário" na documentação da API.

Após o cadastro, você deve realizar o login (instruções para isso podem ser encontradas na seção "Login de Usuário" da documentação). Ao fazer o login, será retornado um token de autenticação.

Esse token deve ser utilizado para autenticação nos endpoints protegidos. Para inserir o token, basta acessar o botão "Authorize" na página do Swagger e colá-lo no campo correspondente. Depois disso, você estará autenticado e poderá acessar os recursos da API.


## Endpoints

* usuarios:
    >POST`http://localhost:8080/auth/register`
    
    >POST`http://localhost:8080/auth/login`
    
    >GET`http://localhost:8080/auth/${id}`
    
    >DELETE`http://localhost:8080/auth/${id}`
    
    >PATCH`http://localhost:8080/auth/${id}`

* sensores:

    >POST`http://localhost:8080/sensores`
    
    >GET`http://localhost:8080/sensores/${id}`
    
    >GET`http://localhost:8080/sensores/all/${usuarioId}`
    
    >DELETE`http://localhost:8080/sensores/${id}`
    
    >PUT`http://localhost:8080/sensores/${id}`


* Endpoints que todos podem ter acesso:
    >POST`http://localhost:8080/auth/register`
    
    >POST`http://localhost:8080/auth/login`

* Endpoints que precisam de autenticação com token:
    > GET Usuario`http://localhost:8080/auth/${id}`
    
    > DELETE Usuario`http://localhost:8080/auth/${id}`
    
    > PATCH Usuario`http://localhost:8080/auth/${id}`
    
    > POST Sensor`http://localhost:8080/sensores`
    
    > GET Sensor`http://localhost:8080/sensores/${id}`
    
    > GET Sensor`http://localhost:8080/sensores/all/${usuarioId}`
    
    > DELETE Sensor`http://localhost:8080/sensores/${id}`
    
    > PUT Sensor`http://localhost:8080/sensores/${id}`


### Cadastro de Usuário

- **URL**:
```http
http://localhost:8080/auth/register
```
- **Método**: POST
- **Descrição**: Grava um novo usuário.
- **Requisitos**:

  - Os campos não podem ser nulos.
  - O login é unico.
  - A senha deve conter pelo menos um caractere especial, uma letra maiúscula, uma minuscula, um numero e ter no mínimo 7 caracteres no total.
  - A senha de confirmação deve ser igual à senha informada pelo usuário.

| Campo  | Tipo     | Descrição                                         |
|:-------|:---------|:--------------------------------------------------|
| `nomeCompleto` | `String` | **Obrigatório**. Nome completo do Usuário                  |
| `senha`   | `String`   | **Obrigatório**. Senha do Usuário                 |
| `login`   | `String`   | **Obrigatório**. Login do Usuário                 |
| `senhaConfirmacao`   | `String`   | **Obrigatório**. Senha de Confirmação do Usuário  |

- **Exemplo de JSON para teste**:
```json
{
  "login": "teste",
  "senha": "Teste123@",
  "nomeCompleto": "Livia Freitas Ferreira",
  "senhaConfirmacao": "Teste123@"
}
```

- **response**:
```
usuario criado com sucesso.
```

- **HTTP response status code**:

| HTTP  | Descrição                   |
|:------|:----------------------------|
| `201` | Usuário gravado com sucesso |




### Login

- **URL**:
```http
http://localhost:8080/auth/login
```
- **Método**: POST
- **Descrição**: Realiza o login de um usuário.
- **Requisitos**:

  - O token e id vai servir para utilizar os outros enpoints que precisam de autenticação e o id do usuário para utilizar
  - Só deve ser feito se já tiver o usúario cadastrado, pois assim ele consegue encontrar o usúario.
  - O login está sendo realizado com o método POST pois estamos lidando com dados sensíveis.
  - Os campos não podem ser nulos.
  - O login e a senha informados devem ser iguais aos registrados no banco de dados.

| Campo   | Tipo     | Descrição                         |
|:--------|:---------|:----------------------------------|
| `senha` | `String` | **Obrigatório**. Senha do Usuário |
| `login` | `String`   | **Obrigatório**. Login do Usuário |


- **Exemplo de JSON para teste**:
```json
{
  "login": "teste",
  "senha": "Teste123@"
}
```

- **JSON response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6InRlc3RlIiwiZXhwIjoxNzMxODY4NjMyfQ.FihGRgDKAXUoV7ZKzsK_ZoZXlDMPbnoof3pHAjo5XYI",
  "idUsuario": 2,
  "login": "teste"
}
```

- **HTTP response status code**:

| HTTP  | Descrição                                                                                            |
|:------|:-----------------------------------------------------------------------------------------------------|
| `200` | Usuário logado com sucesso|


### Atualização de Senha

- **URL**:
```http
http://localhost:8080/auth/${id}
```
- **Método**: PATCH
- **Descrição**: Atualiza a senha de um usuário.
- **Requisitos**:

  - Só deve ser feito se já tiver o usúario cadastrado, pois assim ele consegue encontrar o usúario.
  - Os campos não podem ser nulos.
  - A senha atual e o login devem coincidir com os registrados no banco de dados.
  - A senha nova deve conter pelo menos um caractere especial, uma letra maiúscula, uma minuscula, um numero e ter no mínimo 7 caracteres no total.
  - A senha de confirmação deve ser igual à nova senha informada pelo usuário.
  - A senha nova não deve ser igual a senha atual.
  

- **Exemplo de JSON para teste**:
```json
{
  "senhaAtual": "string",
  "senhaNova": "string",
  "senhaConfirmacao": "string"
}
```

- **JSON response**:
```
Senha alterada com sucesso
```
- **HTTP response status code**:

| HTTP  | Descrição                                                                                            |
|:------|:-----------------------------------------------------------------------------------------------------|
| `200` | Senha alterada com sucesso|


### Buscar usuário por ID

- **URL**:
```http
 http://localhost:8080/auth/${id}
```
- **Método**: GET
- **Descrição**: Retorna dados de um usuário por id.
- **Requisitos**:

  - Só deve ser feito se já tiver o usúario cadastrado, pois assim ele consegue encontrar o usúario.
  - Verifica usuario por ID e retorna dados;

| Parâmetro | Tipo   | Descrição                                  |
|:----------|:-------|:-------------------------------------------|
| `id`      | `Long` | **Obrigatório**. Retorna um usuário por id |

- **JSON response**:
```
{
  "login": "teste",
  "nomeCompleto": "Livia Freitas Ferreira"
}
```

- **HTTP response status code**:

| HTTP  | Descrição                                                                                            |
|:------|:-----------------------------------------------------------------------------------------------------|
| `200` | Usuário encontrado com sucesso|



### Deleta usuário por ID

- **URL**:
```http
 http://localhost:8080/auth/${id}
```
- **Método**: DELETE
- **Descrição**: Deleta um usuário por id.
- **Requisitos**:

  - Só deve ser feito se já tiver o usúario cadastrado, pois assim ele  consegue encontrar o usúario.


| Parâmetro | Tipo   | Descrição                                  |
|:----------|:-------|:-------------------------------------------|
| `id`      | `Long` | **Obrigatório**. Deleta um usuário por id |


- **HTTP response status code**:

| HTTP  | Descrição                                                                                            |
|:------|:-----------------------------------------------------------------------------------------------------|
| `200` | Usuário deletado com sucesso|


### Cadastro de Sensor

- **URL**:
```http
http://localhost:8080/sensores
```
- **Método**: POST
- **Descrição**: Grava um novo sensor.
- **Requisitos**:

- Os campos descricao e localizacao são opcionais


| Campo  | Tipo     | Descrição                                         |
|:-------|:---------|:--------------------------------------------------|
| `tipoSensor` | `String` | **Obrigatório**. Tipo do sensor                  |
| `status`   | `String`   | **Obrigatório**. Status do sensor               |
| `nomeSensor`   | `String`   | **Obrigatório**. Nome do sensor                 |
| `produtoConectado`   | `String`   | **Obrigatório**. Qual produto vai estar conectado ao sensor |
| `descricao`   | `String`   | **Opcional**. Descrição do sensor  |
| `localizacao`   | `String`   | **Opcional**. Localização do sensor |
| `usuarioId`   | `Long`   | **Obrigatório**. Id do usuário existente no banco |

- **Exemplo de JSON para teste**:
```json
{
  "tipoSensor": "string",
  "status": "string",
  "nomeSensor": "string",
  "produtoConectado": "string",
  "descricao": "string",
  "localizacao": "string",
  "usuarioId": 1
}
```

- **response**:
```
{
  "id": 4,
  "nomeSensor": "string",
  "tipoSensor": "string",
  "status": "string",
  "produtoConectado": "string",
  "localizacao": "string"
}
```

- **HTTP response status code**:

| HTTP  | Descrição                   |
|:------|:----------------------------|
| `201` | Sensor gravado com sucesso |



### Atualizar um Sensor

- **URL**:
```http
http://localhost:8080/sensores/${id}
```
- **Método**: PUT
- **Descrição**: Atualiza um sensor.
- **Requisitos**:

  - Deve existir esse id do sensor e id usuario no banco
  - O id do usuario deve ser o mesmo que criou aquele sensor
  - Os campos descricao e localizacao são opcionais


| Campo  | Tipo     | Descrição                                         |
|:-------|:---------|:--------------------------------------------------|
| `tipoSensor` | `String` | **Obrigatório**. Tipo do sensor                  |
| `status`   | `String`   | **Obrigatório**. Status do sensor               |
| `nomeSensor`   | `String`   | **Obrigatório**. Nome do sensor                 |
| `produtoConectado`   | `String`   | **Obrigatório**. Qual produto vai estar conectado ao sensor |
| `descricao`   | `String`   | **Opcional**. Descrição do sensor  |
| `localizacao`   | `String`   | **Opcional**. Localização do sensor |
| `usuarioId`   | `Long`   | **Obrigatório**. Id do usuário existente no banco |

- **Exemplo de JSON para teste**:
```json
{
  "tipoSensor": "string",
  "status": "string",
  "nomeSensor": "string",
  "produtoConectado": "string",
  "descricao": "string",
  "localizacao": "string",
  "usuarioId": 1
}
```

- **response**:
```
{
  "id": 4,
  "nomeSensor": "string",
  "tipoSensor": "string",
  "status": "string",
  "produtoConectado": "string",
  "localizacao": "string"
}
```

- **HTTP response status code**:

| HTTP  | Descrição                   |
|:------|:----------------------------|
| `200` | Sensor atualizado com sucesso |



### Buscar sensor por ID

- **URL**:
```http
http://localhost:8080/sensores/${id}
```
- **Método**: GET
- **Descrição**: Retorna dados de um sensor por id.
- **Requisitos**:

  - Só deve ser feito se já tiver o sensor cadastrado, pois assim ele consegue encontrar o sensor.
  - Verifica sensor por ID e retorna dados;

| Parâmetro | Tipo   | Descrição                                  |
|:----------|:-------|:-------------------------------------------|
| `id`      | `Long` | **Obrigatório**. Retorna um sensor por id |


- **JSON response**:
```
{
  "id": 2,
  "nomeSensor": "string",
  "tipoSensor": "string",
  "status": "string",
  "produtoConectado": "dfsdf",
  "localizacao": "string",
  "descricao": "string",
  "dataCriacao": "2024-11-15T20:54:29.201945",
  "dataModificacao": "2024-11-15T21:11:53.895381"
}
R
```

- **HTTP response status code**:

| HTTP  | Descrição                                                                                            |
|:------|:-----------------------------------------------------------------------------------------------------|
| `200` | Sensor encontrado com sucesso|


### Busca lista de sensor por ID usuário

- **URL**:
```http
http://localhost:8080/sensores/all/${usuarioId}
```
- **Método**: GET
- **Descrição**: Retorna lista de sensor por id usuario.
- **Requisitos**:

  - Só deve ser feito se já tiver o sensor cadastrado, pois assim ele  consegue encontrar o sensor.
  - Verifica usuario por ID e retorna a lista de sensores;

| Parâmetro | Tipo   | Descrição                                  |
|:----------|:-------|:-------------------------------------------|
| `id`      | `Long` | **Obrigatório**. Retorna a lista de sensor desse usuário |
| `page`      | `int` | **Opcional**. Retorna a lista de sensor dessa página |
| `size`      | `int` | **Opcional**. Retorna a lista de sensor de acordo com quantos sensores você quer que retorne |
| `sortBy`      | `string` | **Opcional**. Retorna a lista de sensor de acordo com a coluna que você quer ordenar |
| `direction`      | `string` | **Opcional**. Retorna a lista de sensor de acordo com a ordenação que você quer, exemplo asc(ordena de forma crescente) ou desc((ordena de forma decrescente.)) |


- **JSON response**:
```
{
  "content": [
    {
      "id": 2,
      "nomeSensor": "string",
      "tipoSensor": "string",
      "status": "string",
      "produtoConectado": "dfsdf",
      "localizacao": "string",
      "descricao": "string"
    },
    {
      "id": 3,
      "nomeSensor": "string",
      "tipoSensor": "string",
      "status": "string",
      "produtoConectado": "string",
      "localizacao": null,
      "descricao": null
    },
    {
      "id": 4,
      "nomeSensor": "string",
      "tipoSensor": "string",
      "status": "string",
      "produtoConectado": "string",
      "localizacao": "string",
      "descricao": "string"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 3,
  "totalPages": 1,
  "first": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 3,
  "empty": false
}
```

- **HTTP response status code**:

| HTTP  | Descrição                                                                                            |
|:------|:-----------------------------------------------------------------------------------------------------|
| `200` | Lista de sensores encontrada com sucesso|



### Deleta sensor por ID

- **URL**:
```http
 http://localhost:8080/auth/${id}
```
- **Método**: DELETE
- **Descrição**: Deleta um sensor por id.
- **Requisitos**:

  - Só deve ser feito se já tiver o sensor cadastrado, pois assim ele consegue encontrar o sensor.


| Parâmetro | Tipo   | Descrição                                  |
|:----------|:-------|:-------------------------------------------|
| `id`      | `Long` | **Obrigatório**. Deleta um sensor por id |


- **HTTP response status code**:

| HTTP  | Descrição                                                                                            |
|:------|:-----------------------------------------------------------------------------------------------------|
| `200` | Sensor deletado com sucesso|
