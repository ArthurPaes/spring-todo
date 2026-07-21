package com.sicredi.todo.dto;

import java.time.Instant;

public class TodoResponse {
    private Long id;
    private String title;
    private Instant createdAt;
    private Boolean completed;
    private Integer priority;

    public TodoResponse() {
    }

    public TodoResponse(Long id, String title, Instant createdAt, Boolean completed, Integer priority) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.completed = completed;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}
