package com.sicredi.todo.repository;

import com.sicredi.todo.entity.Todo;
import org.springframework.data.jpa.domain.Specification;

// Each method returns a single, reusable "WHERE condition" as a Specification.
// Nothing here runs any query -- these are just descriptions of a condition,
// combined later at runtime in the service.
public class TodoSpecifications {

    private TodoSpecifications() {
        // utility class: only static methods, never instantiated
    }

    public static Specification<Todo> hasCompleted(boolean completed) {
        return (root, query, cb) -> cb.equal(root.get("completed"), completed);
    }

    public static Specification<Todo> titleContains(String search) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%");
    }
}
