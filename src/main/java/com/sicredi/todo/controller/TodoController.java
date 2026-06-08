package com.sicredi.todo.controller;

import com.sicredi.todo.dto.CreateTodoRequest;
import com.sicredi.todo.entity.Todo;
import com.sicredi.todo.service.TodoService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getTodos() {
        return service.findAll();
    }

    @PostMapping
    public void createTodo(@RequestBody CreateTodoRequest requestBody) {

        service.createTodo(
                requestBody.getTitle());

    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {

        service.deleteTodo(id);

    }

}
