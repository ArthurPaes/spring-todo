package com.sicredi.todo.mapper;

import com.sicredi.todo.dto.TodoResponse;
import com.sicredi.todo.entity.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoResponse toResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle());
    }

}
