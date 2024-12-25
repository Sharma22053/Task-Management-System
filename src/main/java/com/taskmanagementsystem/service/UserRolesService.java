package com.taskmanagementsystem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.taskmanagementsystem.dto.UserRoleProjection;
import com.taskmanagementsystem.entity.UserRoles;
import com.taskmanagementsystem.entity.UserRolesId;
import com.taskmanagementsystem.exception.UserRoleOperationException;
import com.taskmanagementsystem.repository.UserRoleRepository;
import com.taskmanagementsystem.repository.UserRolesRepository;

@Service
public class UserRolesService {

   
	private final UserRolesRepository userRolesRepository;
	
	private final UserRoleRepository userRoleRepository;
    
    public UserRolesService(UserRolesRepository userRolesRepository,UserRoleRepository userRoleRepository) {
    	this.userRoleRepository = userRoleRepository;
    	this.userRolesRepository =userRolesRepository;
    }


    public Map<String,String> assignUserRole(UserRoles userRoles) {
        UserRolesId id = userRoles.getId();
        if (userRolesRepository.existsById(id)) {
            throw new UserRoleOperationException("ADDFAILS", "UserRole already exists");
        }
        userRolesRepository.save(userRoles);
        Map<String, String> response = new HashMap<>();
        response.put("code", "POSTSUCCESS");
        response.put("message", "UserRole added successfully");
        return response;
    }

    public List<UserRoleProjection> getAllRoles() {
        return userRoleRepository.findAllProjectedBy();
    }
    public List<UserRoleProjection> getUserRolesByUserId(int userId) {
        List<UserRoleProjection> userRoles = userRolesRepository.findRolesByUserId(userId);
        if (userRoles.isEmpty()) {
            throw new UserRoleOperationException("GETFAILS", "UserRole doesn't exist for user ID: " + userId);
        }
        return userRoles;
    }

    public Map<String,String> revokeUserRole(int userRoleId, int userId) {
        UserRolesId id = new UserRolesId(userId, userRoleId);
        Optional<UserRoles> userRoles = userRolesRepository.findById(id);
        if (userRoles.isEmpty()) {
            throw new UserRoleOperationException("DLTFAILS", "UserRole doesn't exist for the given ID");
        }
        userRolesRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("code", "DELETESUCCESS");
        response.put("message", "UserRole deleted successfully");
        return response;
    }
}