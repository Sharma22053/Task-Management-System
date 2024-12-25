package com.taskmanagementsystem.dto;

public interface UserLoginProjection {
    Integer getUserId();
    String getUsername();
    String getPassword();
    String getRoleName();
    String getFullName();
}