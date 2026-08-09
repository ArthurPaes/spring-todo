package com.sicredi.todo.mapper;

import com.sicredi.todo.dto.UserResponse;
import com.sicredi.todo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName());
    }

}
