package com.example.spring_ai_chatbot;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User not found with ID " + id);
    }
}