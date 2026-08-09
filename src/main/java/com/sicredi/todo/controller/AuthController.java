package com.sicredi.todo.controller;

import com.sicredi.todo.dto.RegisterRequest;
import com.sicredi.todo.dto.UserResponse;
import com.sicredi.todo.entity.User;
import com.sicredi.todo.mapper.UserMapper;
import com.sicredi.todo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;
    private final UserMapper mapper;

    public AuthController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest requestBody) {

        User created = service.register(requestBody);
        UserResponse body = mapper.toResponse(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
