package com.taskmanagementsystem.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "taskcategory")
public class TaskCategory {

    @EmbeddedId
    private TaskCategoryId id;

    @ManyToOne
    @JoinColumn(name = "TaskID", insertable = false, updatable = false)
    private Tasks task;  

    @ManyToOne
    @JoinColumn(name = "CategoryID", insertable = false, updatable = false)
    private Category category;  

    // Constructors
    public TaskCategory() {}

    public TaskCategory(TaskCategoryId id, Tasks task, Category category) {
        this.id = id;
        this.task = task;
        this.category = category;
    }

    // Getters and setters
    public TaskCategoryId getId() {
        return id;
    }

    public void setId(TaskCategoryId id) {
        this.id = id;
    }

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
