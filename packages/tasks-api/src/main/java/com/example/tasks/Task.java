package com.example.tasks;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "task_id_sequence", sequenceName = "task_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "task_id_sequence", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    @Column(name = "priority", nullable = false)
    private int priority = 0;

    public Task() {
    }

    public Task(long id, String description, boolean completed, int priority) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description=" + description +
                ", completed=" + completed +
                '}';
    }
}
