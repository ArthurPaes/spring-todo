package com.sicredi.todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "email must not be empty")
    @Email(message = "email must be a valid email address")
    private String email;

    @NotBlank(message = "name must not be empty")
    private String name;

    @NotBlank(message = "password must not be empty")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
