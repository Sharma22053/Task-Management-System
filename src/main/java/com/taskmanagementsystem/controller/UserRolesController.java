package com.taskmanagementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagementsystem.dto.UserRoleProjection;
import com.taskmanagementsystem.entity.UserRoles;
import com.taskmanagementsystem.service.UserRolesService;

@CrossOrigin(origins = {"http://localhost:4200"}) // Allows cross-origin requests from Angular frontend.
@RestController
@RequestMapping("/api/userroles")
public class UserRolesController {

	// Constructor-based dependency injection ensures immutability and easier testing.
	private final UserRolesService userRolesService;

    public UserRolesController(UserRolesService userRolesService) {
        this.userRolesService = userRolesService;
    }

    @PostMapping("/assign") //http://localhost:8091/api/userroles/assign
    public ResponseEntity<Map<String, String>> assignUserRole(@RequestBody UserRoles userRoles) {
        Map<String,String> response = userRolesService.assignUserRole(userRoles);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all") //http://localhost:8091/api/userroles/all
    public ResponseEntity<List<UserRoleProjection>> getAllUserRoles() {
        List<UserRoleProjection> userRoles = userRolesService.getAllRoles();
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")  //http://localhost:8091/api/userroles/user/{userId}
    public ResponseEntity<List<UserRoleProjection>> getUserRolesByUserId(@PathVariable int userId) {
        List<UserRoleProjection> userRoles = userRolesService.getUserRolesByUserId(userId);
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @DeleteMapping("/revoke/{userRoleId}/{userId}")  //http://localhost:8091/api/userroles/revoke/{userRoleId}/{userId}
    public ResponseEntity<Map<String, String>> revokeUserRole(@PathVariable int userRoleId, @PathVariable int userId) {
        Map<String,String> successResponse = userRolesService.revokeUserRole(userRoleId, userId);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
