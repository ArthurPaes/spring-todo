package com.sicredi.todo.controller;

import com.sicredi.todo.dto.PagedResponse;
import com.sicredi.todo.dto.TodoResponse;
import com.sicredi.todo.mapper.TodoMapper;
import com.sicredi.todo.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final TodoService todoService;
    private final TodoMapper mapper;

    public UserController(TodoService todoService, TodoMapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    @Operation(summary = "List a specific user's todos, with optional pagination, sorting, and filtering")
    @GetMapping("/{userId}/todos")
    public ResponseEntity<PagedResponse<TodoResponse>> getUserTodos(
            @PathVariable Long userId,
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String search) {

        Page<TodoResponse> body = todoService.findByOwner(userId, pageable, completed, search)
                .map(this.mapper::toResponse);

        return ResponseEntity.ok(PagedResponse.from(body));
    }

}
