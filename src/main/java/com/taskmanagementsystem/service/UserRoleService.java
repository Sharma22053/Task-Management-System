package com.taskmanagementsystem.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanagementsystem.entity.UserRole;
import com.taskmanagementsystem.exception.UserRoleOperationException;
import com.taskmanagementsystem.repository.UserRoleRepository;

@Service
public class UserRoleService {

	@Autowired
	UserRoleRepository userRoleRepository;

	public Map<String, String> createNewUserRole(UserRole userRole) {
		
		Optional<UserRole> existingUserRole = userRoleRepository.findById(userRole.getUserRoleId());
		if(existingUserRole.isPresent()) {
			throw new UserRoleOperationException("ADDFAILS","Userroles already exist");
		}
		userRoleRepository.save(userRole);

		Map<String, String> successResponse = new LinkedHashMap<>();
		    successResponse.put("code", "POSTSUCCESS");
		    successResponse.put("message", "UserRoles added successfully"); 
		    return successResponse;
	}

	public List<UserRole> getAllUserRole() {
		List<UserRole> listUserRole = userRoleRepository.findAll();
		if(listUserRole.isEmpty()) {
			throw new UserRoleOperationException("GETFAILS","Userroles is empty");
		}
		
		return listUserRole;
	}

	public UserRole getDetailsByUserId(int userRoleId) {
		Optional<UserRole> optionalUserRole = userRoleRepository.findById(userRoleId);
	   
	    return optionalUserRole.orElseThrow(() -> 
	        new UserRoleOperationException("GETFAILS", "User role doesn't exist"));
	}

	public Map<String, String> updateUserRole(int userRoleId,UserRole userRole) {
        Optional<UserRole> optionalUserRole = userRoleRepository.findById(userRoleId);

        if (!optionalUserRole.isPresent()) {
            throw new UserRoleOperationException("UPDATEFAIL", "UserRole does not exist");
        }

        UserRole existinguserRole = optionalUserRole.get();
        existinguserRole.setRoleName(userRole.getRoleName());
        userRoleRepository.save(existinguserRole);

        
        Map<String, String> response = new LinkedHashMap<>();
        response.put("code", "UPDATESUCCESS");
        response.put("status", "USerrole updated successfully");

        return response;
    }

	public Map<String, String> deleteUserRole(int userRoleId) {
       
        Optional<UserRole> optionalUserRole = userRoleRepository.findById(userRoleId);
        if (!optionalUserRole.isPresent()) {
            throw new UserRoleOperationException("DELETEFAIL", "UserRole does not exist");
        }
        userRoleRepository.deleteById(userRoleId);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("code", "DELETESUCCESS");
        response.put("message", "UserRole deleted successfully");

        return response;
    }

}
