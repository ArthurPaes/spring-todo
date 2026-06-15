package com.sicredi.todo.controller;

import com.sicredi.todo.dto.CreateTodoRequest;
import com.sicredi.todo.dto.TodoResponse;
import com.sicredi.todo.entity.Todo;
import com.sicredi.todo.mapper.TodoMapper;
import com.sicredi.todo.service.TodoService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodos() {
        List<TodoResponse> body = service.findAll()
                .stream()
                .map(todo -> this.mapper.toResponse(todo))
                .toList();

        return ResponseEntity.ok(body);

    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {

        service.deleteTodo(id);

        return ResponseEntity.noContent().build();
    }

}
