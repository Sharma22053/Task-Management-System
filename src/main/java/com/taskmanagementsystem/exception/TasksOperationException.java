package com.taskmanagementsystem.exception;

public class TasksOperationException extends RuntimeException {
    private String code; 
    private String message; 
    
    public TasksOperationException(String code, String message) {
       
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
