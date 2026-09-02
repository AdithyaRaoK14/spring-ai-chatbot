package com.example.spring_ai_chatbot;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
@Validated
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {

        String response = chatService.chat(
                request.getConversationId(),
                request.getMessage()
        );

        return new ChatResponse(response);
    }

    @GetMapping(value = "/chat/stream", produces = "text/event-stream")
    public Flux<String> streamChat(
            @RequestParam
            @NotBlank(message = "Conversation ID cannot be empty")
            String conversationId,

            @RequestParam
            @NotBlank(message = "Message cannot be empty")
            String message) {

        return chatService.streamChat(
                conversationId,
                message
        );
    }
}