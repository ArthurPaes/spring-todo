package com.sicredi.todo.service;

import com.sicredi.todo.entity.Todo;
import com.sicredi.todo.exception.TodoNotFoundException;
import com.sicredi.todo.repository.TodoRepository;
import com.sicredi.todo.repository.TodoSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Page<Todo> findAll(Pageable pageable, Boolean completed, String search) {

        Specification<Todo> spec = Specification.unrestricted(); // "no condition yet" -- matches everything

        if (completed != null) {
            spec = spec.and(TodoSpecifications.hasCompleted(completed));
        }

        if (search != null && !search.isBlank()) {
            spec = spec.and(TodoSpecifications.titleContains(search));
        }

        return todoRepository.findAll(spec, pageable);
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
