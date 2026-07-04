# Gerenciador de Tarefas (Kanban)

Este projeto é um gerenciador de tarefas desenvolvido em **Java** com **Spring Boot**, ideal para um quadro Kanban. Ele oferece uma API RESTful para gerenciar tarefas com prioridades e status.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.3.0**
- **Spring Data JPA**
- **H2 Database** (Memória)
- **Maven**
- **JUnit 5 / Mockito** (Testes)

## 📂 Arquitetura

O projeto segue o padrão de camadas:

- **Model:** `Tarefa` (entidade), `StatusTarefa` e `PrioridadeTarefa` (enums).
- **Repository:** `TarefaRepository` (acesso a dados com Spring Data JPA).
- **Service:** `TarefaService` (regras de negócio).
- **Controller:** `TarefaController` (endpoints REST).

## 🛠️ Como Rodar

1. **Compilar e Testar:**
   ```bash
   mvn clean compile test
   ```

2. **Rodar a Aplicação:**
   ```bash
   mvn spring-boot:run
   ```

A API estará disponível em `http://localhost:8080`. O console do H2 está disponível em `/h2-console`.

## 📊 Endpoints da API

- **POST `/tasks`**: Cria uma nova tarefa.
- **GET `/tasks`**: Lista todas as tarefas.
- **GET `/tasks/{id}`**: Busca uma tarefa pelo ID.
- **DELETE `/tasks/{id}`**: Remove uma tarefa pelo ID.

---
*Projeto desenvolvido para estudos.*
