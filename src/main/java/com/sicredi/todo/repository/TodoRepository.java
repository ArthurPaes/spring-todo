package com.sicredi.todo.repository;

import com.sicredi.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TodoRepository
                extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {

        Optional<Todo> findByIdAndOwnerId(Long id, Long ownerId);

}
