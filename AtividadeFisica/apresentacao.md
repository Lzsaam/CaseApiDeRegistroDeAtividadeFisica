# Script de Apresentação (fala em primeira pessoa)

Este roteiro está escrito para eu narrar o projeto durante a apresentação. Cada tópico já traz as frases que vou usar e as referências de código para mostrar na tela.

---

## 0. Abertura e contexto

- “Oi, pessoal! Eu sou o responsável pela API de Registro de Atividades Físicas. O problema que eu precisava resolver era guardar e consultar atividades físicas realizadas pelos colaboradores de forma organizada e fácil de evoluir.”
- “Desde o começo eu decidi aplicar Arquitetura Limpa porque queria separar bem o que é regra de negócio dos detalhes de infraestrutura. Isso me permite testar mais fácil e trocar tecnologia sem impacto.”
- “Antes de entrar em código, deixo uma visão geral: aqui estão os endpoints principais: POST, GET, GET por id, GET por funcional, PUT e DELETE em `/atividades`. Eles estão todos detalhados na coleção Insomnia que eu já deixei pronta.”

---

## 1. Arquitetura Limpa na prática

- “Vou começar mostrando este diagrama simples das camadas (Presentation → Application → Domain ↔ Infrastructure). O importante é perceber que as dependências sempre apontam para o domínio.”
- “Na camada **Domain** (`src/main/java/com/atividade1/atividadefisica/domain`), eu mantenho o coração do negócio: a entidade `PhysicalActivity` (`domain/model/PhysicalActivity.java`) e o contrato `PhysicalActivityRepository` (`domain/repository/PhysicalActivityRepository.java`).”
- “A seguir vem **Application** (`src/main/java/com/atividade1/atividadefisica/application`), onde ficam os casos de uso, por exemplo `RegisterPhysicalActivityUseCase` (`application/usecase/RegisterPhysicalActivityUseCase.java`), `FindPhysicalActivityByIdUseCase` (`application/usecase/FindPhysicalActivityByIdUseCase.java`) e `UpdatePhysicalActivityUseCase` (`application/usecase/UpdatePhysicalActivityUseCase.java`). Eles orquestram cada cenário sem saber nada de banco de dados ou HTTP.”
- “A camada **Infrastructure** (`src/main/java/com/atividade1/atividadefisica/infrastructure`) traz os detalhes técnicos. Aqui eu tenho o adapter `PhysicalActivityRepositoryAdapter` (`infrastructure/persistence/adapter/PhysicalActivityRepositoryAdapter.java`) implementando o contrato do domínio, e a entidade JPA `PhysicalActivityEntity` (`infrastructure/persistence/entity/PhysicalActivityEntity.java`).”
- “Por fim, a camada **Presentation** (`src/main/java/com/atividade1/atividadefisica/presentation`) expõe os endpoints REST no `PhysicalActivityController` (`presentation/controller/PhysicalActivityController.java`). Ela usa DTOs (`presentation/dto/PhysicalActivityRequest.java`, `presentation/dto/PhysicalActivityResponse.java`) e o mapper `PhysicalActivityPresenterMapper` (`presentation/mapper/PhysicalActivityPresenterMapper.java`) para converter dados.”
- “Deixa eu passar rapidamente pelo fluxo do `POST /atividades`: o controller recebe o JSON, transforma em comando com o mapper (`toCommand`), o caso de uso `RegisterPhysicalActivityUseCase` valida e chama o `PhysicalActivityRepository`, e o adapter persiste via JPA. A resposta volta convertida em DTO.”
- “Esse desenho garante três benefícios imediatos: testes isolados por camada, possibilidade de trocar H2 por PostgreSQL sem tocar na regra de negócio, e evolução incremental sem efeito cascata.”
- “Antes de seguir, apenas para estruturar mentalmente: cada caso de uso corresponde a um método HTTP do controller. `POST /atividades` chama o `RegisterPhysicalActivityUseCase`, `GET /atividades` e `GET /atividades/{id}` usam `ListAllPhysicalActivitiesUseCase` e `FindPhysicalActivityByIdUseCase`, `GET /atividades/funcional/{funcional}` aciona `ListPhysicalActivitiesByFunctionalUseCase`, o `PUT /atividades/{id}` chama `UpdatePhysicalActivityUseCase`, e o `DELETE /atividades/{id}` orquestra `DeletePhysicalActivityUseCase`. Assim, a arquitetura limpa fica evidente na própria rota.”

---

## 2. Design Patterns que eu utilizei

- “Primeiro, o **Builder Pattern**. No arquivo `src/main/java/com/atividade1/atividadefisica/domain/model/PhysicalActivity.java` eu exponho `PhysicalActivity.builder()`. Isso me permite criar atividades válidas com leitura fluida, tanto nos casos de uso quanto nos testes.”
- “Depois, o **Repository Pattern** combinado com Adapter. O domínio define `PhysicalActivityRepository` (`src/main/java/com/atividade1/atividadefisica/domain/repository/PhysicalActivityRepository.java`). A implementação real está em `src/main/java/com/atividade1/atividadefisica/infrastructure/persistence/adapter/PhysicalActivityRepositoryAdapter.java`, que traduz a entidade de domínio para a entidade JPA `PhysicalActivityEntity` (`infrastructure/persistence/entity/PhysicalActivityEntity.java`).”
- “Também usei o **Mapper/DTO Pattern**. Em `src/main/java/com/atividade1/atividadefisica/presentation/mapper/PhysicalActivityPresenterMapper.java` eu converto request → command e domínio → response. Com isso, o controller fica magro e desacoplado dos detalhes internos.”
- “Por último, o **Command Object**. Reparem em `src/main/java/com/atividade1/atividadefisica/application/dto/RegisterPhysicalActivityCommand.java` e `src/main/java/com/atividade1/atividadefisica/application/dto/UpdatePhysicalActivityCommand.java`. Eles encapsulam os dados da ação e centralizam validações, o que facilita testes e manutenção.”

---

## 3. Princípios SOLID em ação

- “S de Single Responsibility: cada classe faz uma coisa só. Um exemplo claro é o `RegisterPhysicalActivityUseCase` (`src/main/java/com/atividade1/atividadefisica/application/usecase/RegisterPhysicalActivityUseCase.java`), que só cuida do registro. Já o `GlobalExceptionHandler` (`src/main/java/com/atividade1/atividadefisica/presentation/exception/GlobalExceptionHandler.java`) cuida exclusivamente do tratamento de erros.”
- “O de Open/Closed: quando eu precisar de um novo fluxo, como listar atividades por data, eu só adiciono um novo caso de uso (em `application/usecase`) e um método no repositório (`domain/repository/PhysicalActivityRepository.java`); o restante permanece fechado para modificação.”
- “L de Liskov Substitution: se eu trocar `PhysicalActivityRepositoryAdapter` (`infrastructure/persistence/adapter`) por uma implementação em memória, os casos de uso continuam funcionando porque eles dependem apenas da interface do domínio (`domain/repository/PhysicalActivityRepository.java`).”
- “I de Interface Segregation: `PhysicalActivityRepository` (`domain/repository/PhysicalActivityRepository.java`) expõe só os métodos necessários (save, findAll, findById, deleteById...). Não existe uma interface gigantesca com métodos inúteis.”
- “D de Dependency Inversion: todos os casos de uso recebem `PhysicalActivityRepository` como abstração. O Spring injeta a implementação concreta (`PhysicalActivityRepositoryAdapter`), e nos testes (`src/test/java/com/atividade1/atividadefisica/presentation/PhysicalActivityControllerIntegrationTest.java`) eu consigo simular com mocks sem esforço.”

---

## 4. Qualidade, testes e monitoramento

- “Para garantir qualidade, eu escrevi testes de integração em `src/test/java/com/atividade1/atividadefisica/presentation/PhysicalActivityControllerIntegrationTest.java`. Eles exercitam POST, GET, PUT e DELETE usando MockMvc contra o contexto real.”
- “A validação de entrada e o tratamento de erros ficam em `src/main/java/com/atividade1/atividadefisica/presentation/dto/PhysicalActivityRequest.java` com Bean Validation e `src/main/java/com/atividade1/atividadefisica/presentation/exception/GlobalExceptionHandler.java` para consolidar respostas amigáveis.”
- “Em termos de próximos passos técnicos, já deixei mapeado o uso de Spring Actuator para métricas, logs estruturados e talvez um rastreamento distribuído se a API crescer.”

---

## 5. Demonstração prática

- “Agora eu mostro rapidamente a coleção Insomnia (`docs/insomnia-export.json`). Primeiro eu disparo o `POST /atividades` com o payload base, em seguida `GET /atividades` para ver o registro, depois `PUT /atividades/{id}` para atualizar e finalizo com `DELETE /atividades/{id}`.”
- “Quando eu precisar conferir direto no banco, eu acesso `http://localhost:8080/h2-console`, uso a URL `jdbc:h2:mem:atividadefisica`, usuário `sa` e senha vazia. A consulta `SELECT * FROM PHYSICAL_ACTIVITIES;` mostra os mesmos dados que a API retorna.”

---

## 6. Fechamento e roadmap

- “Para fechar, eu reforço os pilares que sustentam o projeto: arquitetura limpa para desacoplamento, padrões de projeto para manter o código organizado, SOLID para garantir extensibilidade, e testes automatizados para confiabilidade.”
- “Os próximos passos naturais são adicionar autenticação e autorização, configurar perfis para usar um banco externo como PostgreSQL e incluir auditoria ou notificações.”
- “Deixo vocês à vontade para explorar o repositório: o `README.md` tem instruções completas, a coleção Insomnia está em `docs`, e este roteiro em `apresentacao.md`.”
- “Obrigado! Agora eu fico aberto para dúvidas.”

> Observação para mim: sempre levar na apresentação um diagrama simples das camadas e snippets curtos dos arquivos citados para reforçar visualmente cada argumento.
