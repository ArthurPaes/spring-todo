package com.sicredi.todo.service;

import com.sicredi.todo.entity.Todo;
import com.sicredi.todo.exception.TodoNotFoundException;
import com.sicredi.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo createTodo(
            String title) {

        Todo todo = new Todo(title);

        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }

        todoRepository.deleteById(id);
    }

}
