package com.taskmanagementsystem.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(UserOperationException.class)
	 public ResponseEntity<Map<String,String>> handleUserOperationException(UserOperationException ex){
		 Map<String, String> errorResponse = new LinkedHashMap<>();
	        errorResponse.put("code", ex.getCode());
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler(TasksOperationException.class)
	 public ResponseEntity<Map<String,String>> handleTasksOperationException(TasksOperationException ex){
		 Map<String,String> errorResponse = new LinkedHashMap<>();
		 errorResponse.put("code", ex.getCode());
		 errorResponse.put("message", ex.getMessage());
		 
		 return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler(ProjectOperationException.class)
	 public ResponseEntity<Map<String,String>> handleProjectOperationException(ProjectOperationException ex){
		 Map<String,String> errorResponse = new LinkedHashMap<>();
		 errorResponse.put("code", ex.getCode());
		 errorResponse.put("message", ex.getMessage());
		 
		 return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler(NotificationOperationException.class)
	 public ResponseEntity<Map<String,String>> handleNotificationOperationException(NotificationOperationException ex){
		 Map<String,String> errorResponse = new LinkedHashMap<>();
		 errorResponse.put("code", ex.getCode());
		 errorResponse.put("message", ex.getMessage());
		 
		 return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler(CommentOperationException.class)
	 public ResponseEntity<Map<String,String>> handleCommentOperationException(CommentOperationException ex){
		 Map<String, String> errorResponse = new LinkedHashMap<>();
	        errorResponse.put("code", ex.getCode());
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler(CategoryOperationException.class)
	 public ResponseEntity<Map<String,String>> handleCategoryOperationException(CategoryOperationException ex){
		 Map<String, String> errorResponse = new LinkedHashMap<>();
	        errorResponse.put("code", ex.getCode());
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler(UserRoleOperationException.class)
	 public ResponseEntity<Map<String,String>> handleUserRoleOperationException(UserRoleOperationException ex){
		 Map<String, String> errorResponse = new LinkedHashMap<>();
	        errorResponse.put("code", ex.getCode());
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	 }
	 
	 
	 @ExceptionHandler(AttachmentOperationException.class)
	 public ResponseEntity<Map<String,String>> handleAttachmentOperationException(AttachmentOperationException ex){
		 Map<String, String> errorResponse = new LinkedHashMap<>();
	        errorResponse.put("code", ex.getCode());
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	 }
	 
	 @ExceptionHandler(TaskCategoryOperationException.class)
	 public ResponseEntity<Map<String,String>> handleTaskCategoryOperationException(TaskCategoryOperationException ex){
		 Map<String, String> errorResponse = new LinkedHashMap<>();
	        errorResponse.put("code", ex.getCode());
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	 }
	 
	
	 @ExceptionHandler( UserRolesOperationException.class)
	 public ResponseEntity<Map<String,String>> handleUserRolesOperationException( UserRolesOperationException ex){
		 Map<String, String> errorResponse = new LinkedHashMap<>();
	        errorResponse.put("code", ex.getCode());
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	 }
}