package com.example.spring_ai_chatbot;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {

    @Tool(description = "Add two numbers together")
    public double add(
            @ToolParam(description = "The first number")
            double a,

            @ToolParam(description = "The second number")
            double b) {

        System.out.println(
                "Calculator tool called: add(" + a + ", " + b + ")"
        );

        return a + b;
    }
}