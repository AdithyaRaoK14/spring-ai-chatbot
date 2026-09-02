package com.example.spring_ai_chatbot;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        User user = userService.findUser(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return user;
    }
}