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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagementsystem.dto.UserLoginProjection;
import com.taskmanagementsystem.dto.UserProjection;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = {"http://localhost:4200"})  /*my server is allowing this domain to access it's resources*/
@RestController /*@Controller + @ResponseBody*/
@Tag(name = "User Controller", description = "Handles user-related operations")
@RequestMapping("/api/users")     /*It will handle HTTP requests*/
public class UserController {

	
	private final UserService userService;  /*Constructor based injection for better immutability and testability*/

	public UserController(UserService userService) {
		this.userService = userService;
	}


	@PostMapping(value = "/post")
	public ResponseEntity<Map<String,Object>> createNewUser(@RequestBody User user){   /* This annotation @RequestBody will bind the HTTP request body to this method parameter. */

		return new ResponseEntity<>(userService.createNewUser(user), HttpStatus.CREATED);    /*ResponseEntity includes status code , headers and body*/
	}
	
	@Operation(
		    summary = "Get all users",
		    description = "Fetch a list of all registered users"
		)
	@GetMapping(value = "/all")     /*http://localhost:8091/api/users/all*/
	public ResponseEntity<List<UserProjection>> getListOfAllUsers() {
		List<UserProjection> users = userService.getListOfAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{userId}")      /*http://localhost:8091/api/users/{userID}*/
	public ResponseEntity<UserProjection> getUserByUserId(@PathVariable int userId){
		UserProjection user = userService.getUserByUserId(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/email-domain/{domain}")    /*http://localhost:8091/api/users/email-domain/{@example.com}*/
	public ResponseEntity<UserProjection> getUserByEmail(@PathVariable("domain") String email){
		UserProjection user = userService.getUserByEmail(email);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@GetMapping("/search/{name}")    /*http://localhost:8091/api/users/search/{john_doe}*/
	public ResponseEntity<UserProjection> getUserByName(@PathVariable("name") String userName){
		UserProjection user = userService.getUserByName(userName);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	@GetMapping("/most-tasks")     /*http://localhost:8091/api/users/most-tasks*/
	public ResponseEntity<UserProjection> getUsersWithMostTasks(){
		
		return new ResponseEntity<>(userService.getUsersWithMostTasks(),HttpStatus.OK);
	}
	
	@GetMapping("/authenticate")   /*http://localhost:8091/api/users/authenticate*/
	public ResponseEntity<String> authenticateUser(@RequestParam String username, @RequestParam String password){

		return new ResponseEntity<>(userService.authenticateUser(username,password),HttpStatus.OK);
	}
	
	@GetMapping("/completed-tasks")   /*http://localhost:8091/api/users/completed-tasks*/
	public ResponseEntity<List<UserProjection>> getUsersWithCompletedTasks(){
		
		return new ResponseEntity<>(userService.getUsersWithCompletedTasks(),HttpStatus.OK);
	}

	@PutMapping("/update/{userId}")    /*http://localhost:8091/api/users/update/{userId}*/
	public ResponseEntity<Object> updateUserDetails(@RequestBody User user,@PathVariable int userId) {

		return new ResponseEntity<>(userService.updateUserDetails(user,userId),HttpStatus.OK);
	}

	@DeleteMapping("/delete/{userId}")   /*http://localhost:8091/api/users/delete/{userId}*/
	public ResponseEntity<Object> deleteUser(@PathVariable("userId") int userId) {
		
		return new ResponseEntity<>(userService.deleteUser(userId),HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/login/authenticate")   /*http://localhost:8091/api/users/login/authenticate*/
	public ResponseEntity<List<UserLoginProjection>> authenticateLoginUser(@RequestParam String username, @RequestParam String password){

		return new ResponseEntity<>(userService.authenticateLoginUser(username,password),HttpStatus.OK);
	}
	
}

