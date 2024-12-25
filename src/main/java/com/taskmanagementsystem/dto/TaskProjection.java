package com.taskmanagementsystem.dto;

import java.util.Date;

public interface TaskProjection {
    int getTaskId();
    String getTaskName();
    String getDescription();
    Date getDueDate();
    String getPriority();
    String getStatus();
}
