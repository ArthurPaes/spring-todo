package com.sicredi.todo.service;

import com.sicredi.todo.entity.Todo;
import com.sicredi.todo.exception.TodoNotFoundException;
import com.sicredi.todo.exception.UserNotFoundException;
import com.sicredi.todo.repository.TodoRepository;
import com.sicredi.todo.repository.TodoSpecifications;
import com.sicredi.todo.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
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

    public Page<Todo> findByOwner(Long userId, Pageable pageable, Boolean completed, String search) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        Specification<Todo> spec = TodoSpecifications.hasOwner(userId);

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
