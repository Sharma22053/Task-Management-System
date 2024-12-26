package com.taskmanagementsystem.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanagementsystem.dto.UserLoginProjection;
import com.taskmanagementsystem.dto.UserProjection;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.exception.UserOperationException;
import com.taskmanagementsystem.repository.UserRepository;

@Service
public class UserService {


	private final UserRepository userRepository;
	public UserService(UserRepository userRepository) {

		this.userRepository = userRepository;
	}

	@Transactional    /*Used for maintaning ACID properties of DB*/
	public List<UserProjection> getListOfAllUsers() {
	    List<UserProjection> users = userRepository.findAllProjectedBy();
	    if (users.isEmpty()) {
	        throw new UserOperationException("GETFAILS","User list is empty");
	    }
	    return users;
	}
	
	
	public boolean isUsernameOrEmailTaken(String username, String email) {
		return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
	}
	
	@Transactional
	public Map<String,Object> createNewUser(User user) {
		
		if (isUsernameOrEmailTaken(user.getUsername(), user.getEmail())) {
			throw new UserOperationException("ADDFAILS","User already exist");
	    }
		Map<String, Object> successResponse = new LinkedHashMap<>();  /*End point is expecting a map and the values should be in order like how they are inserted that's why the linkedHashmap*/
	    successResponse.put("code", "POSTSUCCESS");
	    successResponse.put("message", "User created successfully");
	    successResponse.put("user", userRepository.save(user));
		return successResponse;
	}
	
	@Transactional
	public UserProjection getUserByUserId(int userId) {
		return userRepository.findProjectedByUserId(userId)
		            .orElseThrow(() -> new UserOperationException("GETFAILS","User doesn't exist"));
	}

	
	@Transactional
	public UserProjection getUserByEmail(String email) {
		
		 return userRepository.findByProjectedEmail(email) 
		            .orElseThrow(() -> new UserOperationException("GETFAILS","User doesn't exist"));
	}
	
	@Transactional
	public UserProjection getUserByName(String userName) {
	    return userRepository.findByProjectedUsername(userName)
	            .orElseThrow(() -> new UserOperationException("GETFAILS","User doesn't exist"));
	}
	
	
	@Transactional
	public UserProjection getUsersWithMostTasks() {
	    List<User> users = userRepository.findAllUsersWithTasks();
	    if (users.isEmpty()) {
	        throw new UserOperationException("GETFAILS", "No users found");
	    }	
	    User userWithMostTasks = users.stream()
	        .max(Comparator.comparingInt(user -> user.getTask().size()))
	        .orElseThrow(() -> new UserOperationException("GETFAILS", "No tasks found for users"));

	    // Map to UserProjection
	    return new UserProjection() {
	        @Override
	        public Integer getUserId() {
	            return userWithMostTasks.getUserId();
	        }

	        @Override
	        public String getUsername() {
	            return userWithMostTasks.getUsername();
	        }
	        
	        @Override
	        public String getEmail() {
	        	return userWithMostTasks.getEmail();
	        }

			@Override
			public String getFullName() {
				return userWithMostTasks.getFullName();
			}

			@Override
			public String getPassword() {
				return userWithMostTasks.getPassword();
			}
	    };
	}

	
	@Transactional
	public String authenticateUser(String username, String password) {
	   
	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new UserOperationException("ADDFAILS", "User already exist"));

	     if (!user.getPassword().equals(password)) {
	        throw new UserOperationException("ADDFAILS", "Incorrect Password");
	    }
	     
	     return "Authentication Successful";
	}
	
	
	public List<UserProjection> getUsersWithCompletedTasks() {
		
		List<UserProjection> usersWithCompletedTasks = userRepository.findUsersWithCompletedTasks();
		if(usersWithCompletedTasks.isEmpty()) {
			throw new UserOperationException("GETFAILS","User list is empty");
		}
		return usersWithCompletedTasks;
	}
	
	@Transactional
	public Map<String,Object> updateUserDetails(User user,int userId) {
		
		user.setUserId(userId);
		User existingUser = userRepository.findById(user.getUserId())
		        .orElseThrow(() -> new UserOperationException("UPDTFAILS","User doesn't exist"));
		
		if (user.getFullName() != null && !user.getFullName().isEmpty()) {
	        existingUser.setFullName(user.getFullName());
	    }
		
		if (user.getEmail() != null && !user.getEmail().isEmpty()) {
	        
	        if (!isValidEmail(user.getEmail())) {
	            throw new UserOperationException("UPDTFAILS","Email is not properly formatted");
	        }
	        existingUser.setEmail(user.getEmail());
	    }
		if (user.getPassword() != null && !user.getPassword().isEmpty()) {
	        existingUser.setPassword(user.getPassword());
	    }

	    if (user.getUsername() != null && !user.getUsername().isEmpty()) {
	        existingUser.setUsername(user.getUsername());
	    }

	    Map<String, Object> successResponse = new LinkedHashMap<>();
	    successResponse.put("code", "UPDATESUCCESS");
	    successResponse.put("message", "User updated successfully");
	    successResponse.put("Updated User Details", userRepository.save(existingUser));
	    
	    return successResponse;

	}

	private boolean isValidEmail(String email) {
	    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
	    return email.matches(emailRegex);
	}

	@Transactional
	public Map<String,String> deleteUser(int userId) {
		
		User userToDelete = userRepository.findById(userId)
		        .orElseThrow(() -> new UserOperationException("DLTFAILS","User doesn't exist"));
		userRepository.delete(userToDelete);
		 Map<String, String> successResponse = new LinkedHashMap<>();
		    successResponse.put("code", "DELETESUCCESS");
		    successResponse.put("message", "User deleted successfully"); 
		    return successResponse;
		
	}	
	
	@Transactional
	public List<UserLoginProjection> authenticateLoginUser(String username, String password) {
	   
	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new UserOperationException("GETFAILS", "User doesn't exist"));

	     if (!user.getPassword().equals(password)) {
	        throw new UserOperationException("GETFAILS", "Incorrect Password");
	    }
	     
	     List<UserLoginProjection> list = userRepository.authenticateUser(username, password);
	     return list;
	}

}
