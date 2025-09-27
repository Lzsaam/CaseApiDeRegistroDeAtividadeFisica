# API de Registro de Atividades Físicas

API RESTful construída com Spring Boot seguindo princípios de Arquitetura Limpa, SOLID e uso explícito do padrão *Builder* para o agregado de domínio. A solução permite registrar e consultar atividades físicas realizadas por colaboradores, persistindo os dados em um banco H2 em memória.

## ✅ Requisitos atendidos

- **POST `/atividades`**: registra uma nova atividade.
- **GET `/atividades`**: lista todas as atividades armazenadas.
- **GET `/atividades/{id}`**: recupera uma atividade específica.
- **GET `/atividades/funcional/{funcional}`**: lista atividades filtradas por funcional.
- **PUT `/atividades/{id}`**: atualiza uma atividade existente.
- **DELETE `/atividades/{id}`**: remove uma atividade.
- Persistência em **H2** (in-memory).
- Coleção **Insomnia** disponível em `docs/insomnia-export.json`.
- Testes de integração com `MockMvc`.
- Tratamento consistente de erros e validação com Bean Validation.

## 🧱 Arquitetura e boas práticas

A estrutura de pacotes aplica separação em camadas segundo a Arquitetura Limpa:

- `domain`: regras de negócio e entidades (`PhysicalActivity`) usando o padrão *Builder* para manter invariantes.
- `application`: casos de uso (`RegisterPhysicalActivityUseCase`, `ListAllPhysicalActivitiesUseCase`, `FindPhysicalActivityByIdUseCase`, `ListPhysicalActivitiesByFunctionalUseCase`, `UpdatePhysicalActivityUseCase`, `DeletePhysicalActivityUseCase`) orquestrando operações.
- `infrastructure`: adaptações para tecnologias externas (JPA/H2) com o *Repository Pattern* (`PhysicalActivityRepositoryAdapter`).
- `presentation`: camada web com controllers, DTOs, mapeadores e tratamento de exceções.

Princípios SOLID aplicados:

- **Single Responsibility**: cada classe possui uma única responsabilidade clara.
- **Open/Closed**: novos casos de uso podem ser adicionados sem alterar existentes.
- **Liskov**: contratos respeitados entre interface de repositório e adapter.
- **Interface Segregation**: interfaces específicas (`PhysicalActivityRepository`).
- **Dependency Inversion**: casos de uso dependem de abstrações (interfaces de domínio), não de implementações concretas.

## 🚀 Como executar

### Pré-requisitos

- Java 21
- Maven 3.9+

### Passos

```powershell
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

Para acessar o banco via console web: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:atividadefisica`, usuário `sa`, senha vazia).

### Visualizar os dados no console H2

1. Inicie a aplicação (caso ainda não esteja rodando):

2. No navegador, abra `http://localhost:8080/h2-console`.
3. Preencha os campos da tela de login exatamente com:
  - **JDBC URL:** `jdbc:h2:mem:atividadefisica`
  - **User Name:** `sa`
  - **Password:** *(deixe em branco)*
4. Clique em **Connect** e execute, por exemplo, a consulta abaixo para visualizar os registros:

  ```sql
  SELECT * FROM PHYSICAL_ACTIVITIES;
  ```

## 🧪 Testes

```powershell
mvn test
```

Os testes de integração utilizam `MockMvc` e garantem o fluxo completo dos endpoints.

## 📬 Exemplos de requisições

### Registrar atividade

**POST** `http://localhost:8080/atividades`

Payload:

```json
{
  "funcional": "123456",
  "dataHora": "2025-09-24T07:30:00",
  "codigoAtividade": "RUN",
  "descricaoAtividade": "Corrida de 5km"
}
```

### Listar todas as atividades

**GET** `http://localhost:8080/atividades`

![GET listar todas atividades](docs/imagens/GetListarAtividades.png)

### Buscar atividade por ID

**GET** `http://localhost:8080/atividades/1`

![GET Buscar Por id](docs/imagens/GetBuscarPorID.png)

### Listar atividades por funcional

**GET** `http://localhost:8080/atividades/funcional/123456`

![GET Listar por funcional](docs/imagens/Getlistarporfuncional.png)

### Atualizar atividade

**PUT** `http://localhost:8080/atividades/1`

![PUT Atualizar Atividade](docs/imagens/PUT%20atualizar%20atividade.png)

Payload:

```json
{
  "funcional": "654321",
  "dataHora": "2025-09-24T08:00:00",
  "codigoAtividade": "WALK",
  "descricaoAtividade": "Caminhada leve"
}
```

### Remover atividade

**DELETE** `http://localhost:8080/atividades/1`
![DELETE Remover Atividade](docs/imagens/DeletandoInsomnia.png)
![DELETE Remover Atividade h2](docs/imagens/id9removidoH2.png)


## 📂 Estrutura principal

```
src/
 ├─ main/java/com/atividade1/atividadefisica
 │   ├─ domain
 │   ├─ application
 │   ├─ infrastructure
 │   └─ presentation
 └─ test/java/com/atividade1/atividadefisica
     └─ presentation
```


