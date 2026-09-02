package com.example.spring_ai_chatbot;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {

    private final Map<Long, User> users = Map.of(
            1L, new User(1L, "Adithya"),
            2L, new User(2L, "Rahul"),
            3L, new User(3L, "Priya")
    );

    public User findById(Long id) {
        return users.get(id);
    }
}