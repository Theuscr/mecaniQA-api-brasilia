# MecaniQA API - Web API Fundacional

Este repositório contém o código-fonte da Web API Fundacional do sistema MecaniQA. O projeto foi desenvolvido em Java com Spring Boot como parte dos requisitos acadêmicos da avaliação OAT 2, simulando a estruturação backend para a modernização de um sistema de gestão automotiva.

## 1. Arquitetura e Restrições Técnicas

A aplicação foi estruturada seguindo rigorosas restrições arquiteturais para consolidação de fundamentos de Orientação a Objetos e Padrões de Projeto:

* **Linguagem e Framework:** Java com Spring Boot para roteamento RESTful.
* **Padrões de Projeto:** Aplicação do padrão criacional **Builder** para gerenciamento de complexidade de objetos e padrão **Singleton** para repositórios em memória.
* **Separação de Contexto:** Isolamento da camada de domínio através da implementação de Data Transfer Objects (DTOs) e classes Mapper.
* **Restrição Acadêmica (Zero Lombok):** Codificação manual obrigatória de todos os construtores e métodos acessores (Getters/Setters).
* **Persistência:** Gerenciamento de dados realizado estritamente em memória, sem integração com banco de dados nesta etapa.

## 2. Escopo do Desenvolvimento

### OAT 1 - Base Funcional
* CRUD completo para as entidades base: Peças e Serviços.
* Retorno padronizado de Status HTTP (201, 200, 204, 404).

### OAT 2 - Regras de Negócio e Relacionamentos
* **Ordem de Serviço (OS):** Criação e transição de status (Aberto, Em Execução, Executado) utilizando o padrão Builder.
* **Pedido de Peças:** Gestão do ciclo de vida de pedidos (Orçando, Entregue, etc.).
* **Entidade Associativa:** Implementação da classe `ItemPedidoPeca` para possibilitar a alocação de múltiplas peças, com suas respectivas quantidades, a um único pedido.
* **Blindagem da API:** Tráfego de dados realizado exclusivamente via DTOs nas rotas de OS e Pedidos.

## 3. Instruções de Execução

1. Realize o clone deste repositório para o ambiente local.
2. Importe o projeto em sua IDE (recomendado: IntelliJ IDEA ou Eclipse).
3. Execute a classe principal `ApiApplication.java`.
4. O servidor será inicializado localmente. O consumo da API pode ser validado utilizando a coleção estruturada do Postman.

## 4. Documentação UML

Os artefatos de documentação técnica encontram-se disponíveis no repositório:
* **Diagrama de Classes:** Representação estrutural completa contemplando as Entidades, Enums, DTOs, Mappers, Repositories, Controllers e a classe Builder.
* **Diagrama de Atividade:** Modelagem do fluxo lógico e de decisão referente ao endpoint de adição de peças a um pedido (visão do Controller).

---
Equipe: Matheus Costa Rodrigues, Gustavo Prates Caetano, Thainá Araujo Salgado, Vitor Hugo Lima Silva Bittencourt, Nallanda Novato e Vinicius Martins Lebrão.
