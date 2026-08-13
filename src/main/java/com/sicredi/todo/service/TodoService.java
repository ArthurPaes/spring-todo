package com.sicredi.todo.service;

import com.sicredi.todo.entity.Todo;
import com.sicredi.todo.entity.User;
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

    public Page<Todo> findByOwner(User owner, Pageable pageable, Boolean completed, String search) {

        Specification<Todo> spec = TodoSpecifications.hasOwner(owner.getId());

        if (completed != null) {
            spec = spec.and(TodoSpecifications.hasCompleted(completed));
        }

        if (search != null && !search.isBlank()) {
            spec = spec.and(TodoSpecifications.titleContains(search));
        }

        return todoRepository.findAll(spec, pageable);
    }


    public Todo createTodo(String title, User owner) {

        Todo todo = new Todo(title, owner);

        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id, User owner) {

        Todo todo = todoRepository.findByIdAndOwnerId(id, owner.getId())
                .orElseThrow(() -> new TodoNotFoundException(id));

        todoRepository.delete(todo);
    }

}
