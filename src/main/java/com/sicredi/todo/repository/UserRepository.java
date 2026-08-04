package com.sicredi.todo.repository;

import com.sicredi.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    
}
