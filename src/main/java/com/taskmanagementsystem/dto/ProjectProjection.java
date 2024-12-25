package com.taskmanagementsystem.dto;

import java.time.LocalDate;

public interface ProjectProjection {
    Integer getProjectId();
    String getProjectName();
    String getDescription();
    LocalDate getStartDate();
    LocalDate getEndDate();

 
}

