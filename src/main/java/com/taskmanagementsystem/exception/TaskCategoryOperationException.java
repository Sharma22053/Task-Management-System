package com.taskmanagementsystem.exception;

public class TaskCategoryOperationException extends RuntimeException {
    private String code; 
    private String message; 
    
    public TaskCategoryOperationException(String code, String message) {
       
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
