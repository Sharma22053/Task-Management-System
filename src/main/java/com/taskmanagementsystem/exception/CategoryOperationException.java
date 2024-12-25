package com.taskmanagementsystem.exception;

public class CategoryOperationException extends RuntimeException {
    private String code; 
    private String message; 
    
    public CategoryOperationException(String code, String message) {
       
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
