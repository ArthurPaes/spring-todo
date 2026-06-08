package com.sicredi.todo.repository;

import com.sicredi.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository
        extends JpaRepository<Todo, Long> {

}
