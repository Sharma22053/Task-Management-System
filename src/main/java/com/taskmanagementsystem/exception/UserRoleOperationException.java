package com.taskmanagementsystem.exception;

public class UserRoleOperationException extends RuntimeException {
    private String code; 
    private String message; 
    
    public UserRoleOperationException(String code, String message) {
       
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
