package com.sicredi.todo.controller;

import com.sicredi.todo.dto.CreateTodoRequest;
import com.sicredi.todo.dto.PagedResponse;
import com.sicredi.todo.dto.TodoResponse;
import com.sicredi.todo.entity.Todo;
import com.sicredi.todo.mapper.TodoMapper;
import com.sicredi.todo.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.net.URI;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;
    private final TodoMapper mapper;

    public TodoController(TodoService service, TodoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "List todos, with optional pagination, sorting, and filtering by completed status or title search")
    @GetMapping
    public ResponseEntity<PagedResponse<TodoResponse>> getTodos(
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String search) {
        Page<TodoResponse> body = service.findAll(pageable, completed, search)
                .map(this.mapper::toResponse);

        return ResponseEntity.ok(PagedResponse.from(body));

    }

    @Operation(summary = "Create a new todo")
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest requestBody) {

        Todo created = service.createTodo(requestBody.getTitle());
        TodoResponse body = mapper.toResponse(created);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // -> "/todos"
                .path("/{id}") // -> "/todos/{id}"
                .buildAndExpand(created.getId()) // fill {id} with the new id
                .toUri(); // -> URI "/todos/42"

        return ResponseEntity.created(location).body(body);
    }

    @Operation(summary = "Delete a todo by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {

        service.deleteTodo(id);

        return ResponseEntity.noContent().build();
    }

}
