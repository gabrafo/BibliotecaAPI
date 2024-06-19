# Biblioteca API

## Descrição

Este projeto é uma API desenvolvida como parte de um processo seletivo, no entanto, segue sendo atualizada com novos recursos para que possa acompanhar meu desenvolvimento em Java e Spring.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Framework: Security, Data, MVC, etc**
- **Maven**
- **Swagger**
- **JUnit5 e Mockito (testes unitários)**
- **H2 (banco de dados em memória para testes)**
- **PostgreSQL (banco de dados de desenvolvimento)**

## Como Executar Localmente

### Pré-requisitos para construir a aplicação

- Java 17 instalado
- Maven instalado

### Passos: Construção da Imagem/Projeto no servidor local

1. Clone o repositório:

    ```bash
    git clone https://github.com/gabrafo/bibliotecaapi
    cd BibliotecaAPI
    ```

2. Configure as variáveis de ambiente para o banco de dados:

    Crie um arquivo `.env` na raiz do projeto com o valor das variáveis da conexão PostgreSQL instanciada localmente na sua máquina.

    ```env
    db_url=jdbc:postgresql://<SEU_DB_URL>
    db_username=<SEU_DB_USERNAME>
    db_password=<SEU_DB_PASSWORD>
    ```

 ## Acessando os Endpoints na Nuvem/Localmente

 ### Passos: Testando os Endpoints da aplicação

Após construir e executar localmente a aplicação, ou acessar a versão online a partir da URL do Render, você pode testar os endpoints usando o Postman ou qualquer outra ferramenta de cliente HTTP. Aqui estão alguns exemplos de como testar os endpoints principais:

1. **Livro:**
   - **GET:** Recupere todos os livros:
       ```
       GET http://localhost:8080/livro
       ```

   - **GET:** Recupere um livro específico por ID:
       ```
       GET http://localhost:8080/livro/{id}
       ```
       
    - **POST:** Adicione um novo livro:
        ```
        POST http://localhost:8080/livro
        ```
    
    Corpo da requisição (JSON):
       ```
        {
          "autor": "<string>",
          "dataLancamento": "<dateTime>",
          "nome": "<string>",
          "quantidade": "<integer>"
        }
       ```
    
    - **PUT:** Atualize um livro existente por ID:
        ```
        PUT http://localhost:8080/livro/{id}
        ```
    
    Corpo da requisição (JSON):
        ```
        {
          "autor": "<string>",
          "dataLancamento": "<dateTime>",
          "nome": "<string>",
          "quantidade": "<integer>"
        }
        ```
    
    - **DELETE:** Exclua um livro por ID:
        ```
        DELETE http://localhost:8080/livro/{id}
        ```
    
    - **POST:** Empréstimo de um livro por ID:
        ```
        POST http://localhost:8080/livro/{id}/borrow
        ```
    
    Corpo da requisição (JSON):
        ```
        {
          "idLivro": "<long>",
          "idPessoa": "<long>"
        }
        ```
    
    - **POST**: Retorno de um livro por ID:
        ```
        POST http://localhost:8080/livro/{id}/return
        ```
    
    Corpo da requisição (JSON):
        ```
        {
          "idLivro": "<long>",
          "idPessoa": "<long>"
        }
        ```
   
3. **Pessoa:**
