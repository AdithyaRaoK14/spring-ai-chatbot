package com.example.spring_ai_chatbot;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {

    @NotBlank(message = "Conversation ID cannot be empty")
    private String conversationId;

    @NotBlank(message = "Message cannot be empty")
    private String message;

    public ChatRequest() {
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}