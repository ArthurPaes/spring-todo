package com.sicredi.todo.repository;

import com.sicredi.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {
    
    private List<Todo> todos = new ArrayList<>();

    public List<Todo> findAll() {
        return todos;
    }

    public void save(Todo todo) {
        todos.add(todo);
    }

    public void delete(Long id) {
        todos.removeIf(todo -> todo.getId().equals(id));
    }
}
