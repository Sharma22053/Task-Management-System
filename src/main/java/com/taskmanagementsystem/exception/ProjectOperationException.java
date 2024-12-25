package com.taskmanagementsystem.exception;

public class ProjectOperationException extends RuntimeException {
    private String code; 
    private String message; 
    
    public ProjectOperationException(String code, String message) {
       
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
