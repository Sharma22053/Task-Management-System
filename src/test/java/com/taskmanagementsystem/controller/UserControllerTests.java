package com.taskmanagementsystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.taskmanagementsystem.dto.UserLoginProjection;
import com.taskmanagementsystem.dto.UserProjection;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.service.UserService;

class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewUser() {
        User user = new User();
        user.setUsername("testUser");

        Map<String, Object> response = new HashMap<>();
        response.put("code", "CREATED");
        response.put("message", "User created successfully");

        when(userService.createNewUser(user)).thenReturn(response);

        ResponseEntity<Map<String, Object>> result = userController.createNewUser(user);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("CREATED", result.getBody().get("code"));
    }

    @Test
    void testGetListOfAllUsers() {
        List<UserProjection> users = new ArrayList<>();
        when(userService.getListOfAllUsers()).thenReturn(users);

        ResponseEntity<List<UserProjection>> result = userController.getListOfAllUsers();

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void testGetUserByUserId() {
        UserProjection user = mock(UserProjection.class);
        when(userService.getUserByUserId(1)).thenReturn(user);

        ResponseEntity<UserProjection> result = userController.getUserByUserId(1);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void testGetUserByEmail() {
        UserProjection user = mock(UserProjection.class);
        when(userService.getUserByEmail("test@example.com")).thenReturn(user);

        ResponseEntity<UserProjection> result = userController.getUserByEmail("test@example.com");

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void testGetUserByName() {
        UserProjection user = mock(UserProjection.class);
        when(userService.getUserByName("testUser")).thenReturn(user);

        ResponseEntity<UserProjection> result = userController.getUserByName("testUser");

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void testGetUsersWithMostTasks() {
        UserProjection user = mock(UserProjection.class);
        when(userService.getUsersWithMostTasks()).thenReturn(user);

        ResponseEntity<UserProjection> result = userController.getUsersWithMostTasks();

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void testAuthenticateUser() {
        when(userService.authenticateUser("testUser", "password"))
            .thenReturn("Authentication successful");

        ResponseEntity<String> result = userController.authenticateUser("testUser", "password");

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Authentication successful", result.getBody());
    }

    @Test
    void testGetUsersWithCompletedTasks() {
        List<UserProjection> users = new ArrayList<>();
        when(userService.getUsersWithCompletedTasks()).thenReturn(users);

        ResponseEntity<List<UserProjection>> result = userController.getUsersWithCompletedTasks();

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void testDeleteUser() {
        Map<String, String> mockResponse = new HashMap<>();
        mockResponse.put("code", "DELETESUCCESS");
        mockResponse.put("message", "User deleted successfully");

        when(userService.deleteUser(1)).thenReturn(mockResponse);

        ResponseEntity<Object> result = userController.deleteUser(1);

        // Update to use getStatusCode().value()
        assertEquals(200, result.getStatusCode().value()); 

        @SuppressWarnings("unchecked")
        Map<String, String> responseBody = (Map<String, String>) result.getBody();
        assertNotNull(responseBody);
        assertEquals("DELETESUCCESS", responseBody.get("code"));
        assertEquals("User deleted successfully", responseBody.get("message"));
    }



    @Test
    void testAuthenticateLoginUser() {
        List<UserLoginProjection> users = new ArrayList<>();
        when(userService.authenticateLoginUser("testUser", "password"))
            .thenReturn(users);

        ResponseEntity<List<UserLoginProjection>> result = userController.authenticateLoginUser("testUser", "password");

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }
}
