package com.taskmanagementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taskmanagementsystem.dto.UserProjection;
import com.taskmanagementsystem.entity.User;
import com.taskmanagementsystem.exception.UserOperationException;
import com.taskmanagementsystem.repository.UserRepository;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private List<UserProjection> userProjections;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1, "john_doe", "john@example.com", "John Doe", "password");
        userProjections = Arrays.asList(
            new UserProjection() {
                @Override
                public Integer getUserId() { return 1; }
                @Override
                public String getUsername() { return "john_doe"; }
                @Override
                public String getEmail() { return "john@example.com"; }
                @Override
                public String getFullName() { return "John Doe"; }
                @Override
                public String getPassword() { return "password"; }
            }
        );
    }

    @Test
    void testGetListOfAllUsers() {
        when(userRepository.findAllProjectedBy()).thenReturn(userProjections);
        List<UserProjection> result = userService.getListOfAllUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetListOfAllUsers_Empty() {
        when(userRepository.findAllProjectedBy()).thenReturn(Collections.emptyList());
        assertThrows(UserOperationException.class, () -> userService.getListOfAllUsers());
    }

    @Test
    void testCreateNewUser() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        Map<String, Object> response = userService.createNewUser(user);
        assertEquals("POSTSUCCESS", response.get("code"));
        assertEquals("User created successfully", response.get("message"));
    }

    @Test
    void testCreateNewUser_UsernameOrEmailTaken() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        
        assertThrows(UserOperationException.class, () -> userService.createNewUser(user));
    }

    @Test
    void testGetUserByUserId() {
        when(userRepository.findProjectedByUserId(1)).thenReturn(Optional.of(userProjections.get(0)));
        UserProjection result = userService.getUserByUserId(1);
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
    }

    @Test
    void testGetUserByUserId_NotFound() {
        when(userRepository.findProjectedByUserId(1)).thenReturn(Optional.empty());
        assertThrows(UserOperationException.class, () -> userService.getUserByUserId(1));
    }

    @Test
    void testGetUsersWithMostTasks() {
        when(userRepository.findAllUsersWithTasks()).thenReturn(Arrays.asList(user));
        UserProjection result = userService.getUsersWithMostTasks();
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
    }

    
    @Test
    void testDeleteUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));
        
        Map<String, String> response = userService.deleteUser(1);
        assertEquals("DELETESUCCESS", response.get("code"));
        assertEquals("User deleted successfully", response.get("message"));
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UserOperationException.class, () -> userService.deleteUser(1));
    }
}
