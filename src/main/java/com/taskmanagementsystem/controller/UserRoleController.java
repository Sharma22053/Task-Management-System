package com.taskmanagementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagementsystem.entity.UserRole;
import com.taskmanagementsystem.service.UserRoleService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/userrole")
public class UserRoleController {
	
	@Autowired
	UserRoleService userRoleService;
	
	@PostMapping("/post")
	public ResponseEntity<Map<String,String>> createNewUserRole(@RequestBody UserRole userRole){
		
		Map<String,String> successResponse = userRoleService.createNewUserRole(userRole);
		return new ResponseEntity<>(successResponse,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UserRole>> getAllUserRole(){
		
		List<UserRole>successResponse = userRoleService.getAllUserRole();
		return new ResponseEntity<>(successResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{userRoleId}")
	public ResponseEntity<UserRole> getDetailsByUserId(@PathVariable int userRoleId){
		
		UserRole userRoleDetails = userRoleService.getDetailsByUserId(userRoleId);
		return new ResponseEntity<>(userRoleDetails,HttpStatus.OK);
	}
	
	@PutMapping("/{userRoleId}")
	public ResponseEntity<Map<String,String>> updateUserRole(@PathVariable int userRoleId,@RequestBody UserRole userRole){
		
		Map<String,String> successResponse = userRoleService.updateUserRole(userRoleId,userRole);
		return new ResponseEntity<>(successResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{userRoleId}")
	public ResponseEntity<Map<String,String>> deleteUserRole(@PathVariable int userRoleId){
		
		Map<String,String> successResponse = userRoleService.deleteUserRole(userRoleId);
		return new ResponseEntity<>(successResponse,HttpStatus.OK);
	}
}
