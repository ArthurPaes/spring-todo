package com.sicredi.todo.service;

import com.sicredi.todo.dto.RegisterRequest;
import com.sicredi.todo.entity.User;
import com.sicredi.todo.exception.EmailAlreadyInUseException;
import com.sicredi.todo.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyInUseException(request.getEmail());
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), request.getName(), hashedPassword);

        return userRepository.save(user);
    }
}
