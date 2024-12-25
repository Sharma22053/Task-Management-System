package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.controller.UserRoleController;

import com.taskmanagementsystem.entity.UserRole;

import com.taskmanagementsystem.service.UserRoleService;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
 
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
 
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
 
import java.util.HashMap;

import java.util.LinkedHashMap;

import java.util.List;

import java.util.Map;
 
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)

@WebMvcTest(UserRoleController.class) // This will automatically set up MockMvc for the controller

class UserRoleControllerTests {
 
    @MockBean

    private UserRoleService userRoleService; // Mocking the service
 
    @Autowired

    private MockMvc mockMvc;
 
   

    // Test case for fetching all UserRoles

    @Test

    void testGetAllUserRole() throws Exception {

        List<UserRole> userRoles = List.of(new UserRole(1, "Admin"), new UserRole(2, "User"));

        when(userRoleService.getAllUserRole()).thenReturn(userRoles);
 
        mockMvc.perform(get("/api/userrole/all"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].roleName").value("Admin"))

                .andExpect(jsonPath("$[1].roleName").value("User"));
 
        verify(userRoleService, times(1)).getAllUserRole();

    }
 
    // 1. Test for POST: createNewUserRole

    @Test

    void testCreateNewUserRole() throws Exception {

        UserRole userRole = new UserRole(1, "Admin");
 
        // Mock the service method

        when(userRoleService.createNewUserRole(any(UserRole.class))).thenReturn(Map.of("code", "POSTSUCCESS", "message", "UserRole added successfully"));
 
        // Perform a POST request

        mockMvc.perform(post("/api/userrole/post")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"userRoleId\": 1, \"roleName\": \"Admin\"}"))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.code").value("POSTSUCCESS"))

                .andExpect(jsonPath("$.message").value("UserRole added successfully"));
 
        // Verify that the service method was called

        verify(userRoleService, times(1)).createNewUserRole(any(UserRole.class));

    }

 
    //  Test for PUT: updateUserRole

    @Test

    void testUpdateUserRole() throws Exception {

        UserRole userRole = new UserRole(1, "Admin");

        UserRole updatedUserRole = new UserRole(1, "SuperAdmin");

        when(userRoleService.updateUserRole(anyInt(), any(UserRole.class))).thenReturn(Map.of("code", "UPDATESUCCESS", "status", "UserRole updated successfully"));
 
        // Perform the PUT request

        mockMvc.perform(put("/api/userrole/{userRoleId}", 1)

                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"userRoleId\": 1, \"roleName\": \"SuperAdmin\"}"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.code").value("UPDATESUCCESS"))

                .andExpect(jsonPath("$.status").value("UserRole updated successfully"));

        verify(userRoleService, times(1)).updateUserRole(eq(1), any(UserRole.class));

    }

    // 3. Test for GET by ID: getDetailsByUserId

    @Test

    void testGetDetailsByUserId() throws Exception {

        UserRole userRole = new UserRole(1, "Admin");
 
        // Mock the service method to return the real implementation

        when(userRoleService.getDetailsByUserId(1)).thenReturn(userRole);
 
        // Perform the test

        mockMvc.perform(get("/api/userrole/{userRoleId}", 1)

                .contentType(MediaType.APPLICATION_JSON))

            .andExpect(status().isOk())  // Check if status is 200 OK

            .andExpect(jsonPath("$.userRoleId").value(1))  // Check userRoleId

            .andExpect(jsonPath("$.roleName").value("Admin"));  // Check roleName
 
        // Verify that the service method was called once

        verify(userRoleService, times(1)).getDetailsByUserId(1);

    }

    //deleting 

    @Test

    void testDeleteUserRole() throws Exception {

        // Mock the service response

        when(userRoleService.deleteUserRole(1)).thenReturn(Map.of("code", "DELETESUCCESS", "message", "UserRole deleted successfully"));
 
        // Perform DELETE request

        mockMvc.perform(delete("/api/userrole/delete/{userRoleId}", 1))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.code").value("DELETESUCCESS"))

                .andExpect(jsonPath("$.message").value("UserRole deleted successfully"));
 
        // Verify the service method was called

        verify(userRoleService, times(1)).deleteUserRole(1);

    }
 
}
 