package com.example.spring_ai_chatbot;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final CalculatorTools calculatorTools;
    private final UserTools userTools;

    public ChatService(
            ChatClient chatClient,
            CalculatorTools calculatorTools,
            UserTools userTools) {

        this.chatClient = chatClient;
        this.calculatorTools = calculatorTools;
        this.userTools = userTools;
    }

    public String chat(String conversationId, String message) {

        return chatClient
                .prompt()
                .system("You are a helpful Java teacher. Explain concepts simply.")
                .user(message)
                .tools(calculatorTools, userTools)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId
                ))
                .call()
                .content();
    }

    public Flux<String> streamChat(
            String conversationId,
            String message) {

        return chatClient
                .prompt()
                .system("You are a helpful Java teacher. Explain concepts simply.")
                .user(message)
                .tools(calculatorTools, userTools)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId
                ))
                .stream()
                .content();
    }
}