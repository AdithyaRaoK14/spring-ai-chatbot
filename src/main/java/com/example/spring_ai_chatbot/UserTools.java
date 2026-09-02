package com.example.spring_ai_chatbot;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class UserTools {

    private final UserService userService;

    public UserTools(UserService userService) {
        this.userService = userService;
    }

    @Tool(description = "Find a user by their numeric user ID")
    public String getUser(
            @ToolParam(description = "The numeric ID of the user")
            Long id) {

        System.out.println("User tool called: getUser(" + id + ")");

        if (id == null) {
            return "User ID cannot be null";
        }

        if (id <= 0) {
            return "User ID must be greater than 0";
        }

        User user = userService.findUser(id);

        if (user == null) {
            return "No user found with ID " + id;
        }

        return "User ID " + user.getId()
                + " has the name " + user.getName();
    }
}