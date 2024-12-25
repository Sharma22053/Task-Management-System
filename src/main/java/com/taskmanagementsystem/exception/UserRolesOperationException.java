package com.taskmanagementsystem.exception;

public class UserRolesOperationException extends RuntimeException {
    private String code; 
    private String message; 
    
    public UserRolesOperationException(String code, String message) {
       
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
