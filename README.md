# Spring AI Chatbot

A Spring Boot application demonstrating how to build an AI-powered chatbot using **Spring AI**, **Ollama**, and **Qwen 3**.

The project currently focuses on the core backend architecture of an AI chatbot, including conversation memory, streaming responses, tool calling, request validation, exception handling, and REST APIs.

---

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.8**
- **Spring AI 2.0.1**
- **Ollama**
- **Qwen 3 1.7B**
- **Maven**
- **Spring Web MVC**
- **Spring WebFlux**
- **Jakarta Bean Validation**

---

## Features

### AI Chat

The application exposes a REST API for communicating with the Qwen 3 model running locally through Ollama.

```http
POST /api/chat
```

Example request:

```json
{
  "conversationId": "conversation-1",
  "message": "Explain dependency injection in Spring"
}
```

Example response:

```json
{
  "response": "Dependency injection is..."
}
```

---

### Conversation Memory

The chatbot maintains conversation context using Spring AI's `ChatMemory` and `MessageWindowChatMemory`.

A `conversationId` is used to identify a conversation.

For example:

```text
User: My name is Adithya.

User: What is my name?

AI: Your name is Adithya.
```

Different conversation IDs represent different conversations.

The current implementation uses in-memory chat memory, so conversation data is not persisted after the application stops.

---

### Streaming Responses

The application supports streaming AI responses using Server-Sent Events (SSE).

```http
GET /api/chat/stream?conversationId=conversation-1&message=Explain%20Spring%20Boot
```

The response is returned progressively as the model generates it instead of waiting for the complete response.

---

### Tool Calling

The chatbot can use application-defined Java tools when required.

#### Calculator Tool

The calculator tool allows the model to perform calculations.

Example:

```text
User: What is 25 plus 17?

AI: The result of 25 plus 17 is 42.
```

The tool is implemented using Spring AI's `@Tool` annotation.

#### User Lookup Tool

The chatbot can retrieve user information through an application-defined tool.

Example:

```text
User: What is the name of user 1?

AI: User ID 1 has the name Adithya.
```

The tool follows the application's service architecture:

```text
AI
 |
 v
UserTools
 |
 v
UserService
 |
 v
UserRepository
```

This keeps the AI tool separate from the application's business logic.

---

### User REST API

The application also contains a normal REST API for retrieving users.

```http
GET /api/users/{id}
```

Example:

```http
GET /api/users/1
```

Response:

```json
{
  "id": 1,
  "name": "Adithya"
}
```

If the user does not exist:

```json
{
  "status": 404,
  "message": "User not found with ID 99"
}
```

The current user repository uses an in-memory collection for demonstration purposes.

---

### Request Validation

Incoming requests are validated using Jakarta Bean Validation.

For example, an empty message is rejected:

```json
{
  "conversationId": "conversation-1",
  "message": ""
}
```

Response:

```json
{
  "status": 400,
  "message": "Message cannot be empty"
}
```

The streaming endpoint also validates its request parameters.

---

### Global Exception Handling

Application exceptions are handled centrally using `@RestControllerAdvice`.

The current implementation handles:

- Request validation errors
- Constraint violations
- User not found errors

---

## Architecture

The main chatbot request flow is:

```text
Client
  |
  v
ChatController
  |
  v
ChatService
  |
  v
Spring AI ChatClient
  |
  v
Ollama
  |
  v
Qwen 3 1.7B
```

When the model decides that a tool is required:

```text
User Request
     |
     v
ChatClient
     |
     v
Qwen 3
     |
     | Tool Call
     v
Application Tool
     |
     v
Service / Repository
     |
     v
Tool Result
     |
     v
Qwen 3
     |
     v
Final Response
```

---

## Project Structure

```text
spring-ai-chatbot
│
├── .mvn/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/spring_ai_chatbot/
│   │   │       ├── AiConfig.java
│   │   │       ├── CalculatorTools.java
│   │   │       ├── ChatController.java
│   │   │       ├── ChatRequest.java
│   │   │       ├── ChatResponse.java
│   │   │       ├── ChatService.java
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── SpringAiChatbotApplication.java
│   │   │       ├── User.java
│   │   │       ├── UserController.java
│   │   │       ├── UserNotFoundException.java
│   │   │       ├── UserRepository.java
│   │   │       ├── UserService.java
│   │   │       └── UserTools.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── test.http
```

---

## Prerequisites

Install the following before running the application:

- Java 21
- Maven
- Ollama

Install the Qwen 3 model:

```bash
ollama pull qwen3:1.7b
```

Verify the model:

```bash
ollama list
```

Start Ollama if it is not already running:

```bash
ollama serve
```

---

## Configuration

The application connects to Ollama running locally.

`application.properties`:

```properties
spring.application.name=spring-ai-chatbot
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=qwen3:1.7b
```

---

## Running the Application

### 1. Start Ollama

```bash
ollama serve
```

### 2. Run the Spring Boot application

Using Maven:

```bash
mvn spring-boot:run
```

Or using the Maven wrapper on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The application runs on:

```text
http://localhost:8080
```

---

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/chat` | Send a normal chat request |
| `GET` | `/api/chat/stream` | Stream an AI response |
| `GET` | `/api/users/{id}` | Retrieve a user by ID |

---

## API Examples

### Normal Chat

```http
POST http://localhost:8080/api/chat
Content-Type: application/json
```

Request:

```json
{
  "conversationId": "conversation-1",
  "message": "What is dependency injection?"
}
```

---

### Streaming Chat

```http
GET http://localhost:8080/api/chat/stream?conversationId=conversation-1&message=Explain%20Spring%20Boot
```

The endpoint returns a `text/event-stream` response.

---

### Get User

```http
GET http://localhost:8080/api/users/1
```

Response:

```json
{
  "id": 1,
  "name": "Adithya"
}
```

---

## Current Limitations

The current version is intended as a learning and development project.

- User data is stored in memory.
- Chat memory is stored in memory.
- No authentication or authorization is implemented.
- No persistent database is currently used.
- No RAG or vector database is currently implemented.
- Ollama and the Qwen model are expected to run locally.

---

## Future Improvements

Possible future extensions include:

- Persistent conversation memory
- Database-backed user management
- Authentication and authorization
- RAG and document retrieval
- Embeddings and vector databases
- Additional AI tools
- Improved observability and logging
- Production deployment
- Production-grade security and configuration

---

## Learning Goals

This project is being developed to understand how modern AI applications can be integrated with a Spring Boot backend.

The current implementation focuses on:

1. Spring AI fundamentals
2. ChatClient
3. Conversation memory
4. Streaming responses
5. Tool calling
6. REST API design
7. Validation
8. Exception handling
9. Service and repository architecture

---

## References

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [Ollama](https://ollama.com/)
