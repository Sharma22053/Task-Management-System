package com.taskmanagementsystem.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TaskCategoryId implements Serializable {

    @Column(name = "TaskID")
    private int taskId;

    @Column(name = "CategoryID")
    private int categoryId;

    
    public TaskCategoryId() {}

    public TaskCategoryId(int taskId, int categoryId) {
        this.taskId = taskId;
        this.categoryId = categoryId;
    }

    
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskCategoryId that = (TaskCategoryId) o;
        return taskId == that.taskId && categoryId == that.categoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, categoryId);
    }
}

