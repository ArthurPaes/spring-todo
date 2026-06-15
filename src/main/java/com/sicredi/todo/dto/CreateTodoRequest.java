package com.sicredi.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTodoRequest {
    
    @NotBlank(message = "title must not be empty")
    @Size(max = 255, message = "title must be at most 255 characters")
    private String title;

    public CreateTodoRequest() {
    }

    public CreateTodoRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
