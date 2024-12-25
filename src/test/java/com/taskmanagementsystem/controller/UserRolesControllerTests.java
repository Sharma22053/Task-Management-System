package com.taskmanagementsystem.controller;
 
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.HashMap;

import java.util.List;

import java.util.Map;
 
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
import com.fasterxml.jackson.databind.ObjectMapper;

import com.taskmanagementsystem.dto.UserRoleProjection;

import com.taskmanagementsystem.entity.UserRoles;

import com.taskmanagementsystem.service.UserRolesService;
 
@ExtendWith(MockitoExtension.class)

public class UserRolesControllerTests {
 
    @InjectMocks

    private UserRolesController userRolesController;
 
    @Mock

    private UserRolesService userRolesService;
 
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
 
    @BeforeEach

    public void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(userRolesController).build();

        objectMapper = new ObjectMapper();

    }
 
 
    @Test

    public void testRevokeUserRole() throws Exception {

        Map<String, String> response = new HashMap<>();

        response.put("code", "REVOKESUCCESS");

        response.put("message", "Role revoked successfully");
 
        when(userRolesService.revokeUserRole(1, 1)).thenReturn(response);
 
        mockMvc.perform(delete("/api/userroles/revoke/{userRoleId}/{userId}", 1, 1)

                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.code").value("REVOKESUCCESS"))

                .andExpect(jsonPath("$.message").value("Role revoked successfully"));
 
        verify(userRolesService, times(1)).revokeUserRole(1, 1);

    }

    @Test

    public void testGetAllUserRoles() throws Exception {

        // Create mock implementations of UserRoleProjection

        UserRoleProjection role1 = new UserRoleProjection() {

            @Override

            public int getUserRoleId() {

                return 1;

            }
 
            @Override

            public String getRoleName() {

                return "Admin";

            }

        };
 
        UserRoleProjection role2 = new UserRoleProjection() {

            @Override

            public int getUserRoleId() {

                return 2;

            }
 
            @Override

            public String getRoleName() {

                return "User";

            }

        };
 
        // Mock the service response

        when(userRolesService.getAllRoles()).thenReturn(List.of(role1, role2));
 
        // Perform the GET request and verify the response

        mockMvc.perform(get("/api/userroles/all")

                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].userRoleId").value(1))

                .andExpect(jsonPath("$[0].roleName").value("Admin"))

                .andExpect(jsonPath("$[1].userRoleId").value(2))

                .andExpect(jsonPath("$[1].roleName").value("User"));
 
        // Verify the service interaction

        verify(userRolesService, times(1)).getAllRoles();

    }
 
    @Test

    public void testGetUserRolesByUserId() throws Exception {

        // Create a real implementation of UserRoleProjection

        UserRoleProjection role = new UserRoleProjection() {

            @Override

            public int getUserRoleId() {

                return 1;

            }
 
            @Override

            public String getRoleName() {

                return "Admin";

            }

        };
 
        // Mock the service response

        when(userRolesService.getUserRolesByUserId(1)).thenReturn(List.of(role));
 
        // Perform the GET request and verify the response

        mockMvc.perform(get("/api/userroles/user/{userId}", 1)

                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].userRoleId").value(1))

                .andExpect(jsonPath("$[0].roleName").value("Admin"));
 
        // Verify the service interaction

        verify(userRolesService, times(1)).getUserRolesByUserId(1);

    }
 
}

 