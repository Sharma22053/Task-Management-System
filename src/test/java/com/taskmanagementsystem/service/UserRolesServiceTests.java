package com.taskmanagementsystem.service;
 
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
 
import com.taskmanagementsystem.dto.UserRoleProjection;

import com.taskmanagementsystem.entity.UserRoles;

import com.taskmanagementsystem.entity.UserRolesId;

import com.taskmanagementsystem.exception.UserRoleOperationException;

import com.taskmanagementsystem.repository.UserRoleRepository;

import com.taskmanagementsystem.repository.UserRolesRepository;
 
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
 
import java.util.*;
 
@ExtendWith(MockitoExtension.class)

public class UserRolesServiceTests {
 
    @Mock

    private UserRolesRepository userRolesRepository;
 
    @Mock

    private UserRoleRepository userRoleRepository;
 
    @InjectMocks

    private UserRolesService userRolesService;
 
    @BeforeEach

    public void setUp() {

        // Any setup required before each test

    }
 
    // Test for assignUserRole

    @Test

    public void testAssignUserRole_Success() {

        UserRoles userRoles = new UserRoles();

        UserRolesId id = new UserRolesId(1, 1);

        userRoles.setId(id);
 
        when(userRolesRepository.existsById(id)).thenReturn(false);
 
        Map<String, String> result = userRolesService.assignUserRole(userRoles);
 
        assertEquals("POSTSUCCESS", result.get("code"));

        assertEquals("UserRole added successfully", result.get("message"));

        verify(userRolesRepository, times(1)).save(userRoles);

    }
 
    @Test

    public void testAssignUserRole_UserRoleAlreadyExists() {

        UserRoles userRoles = new UserRoles();

        UserRolesId id = new UserRolesId(1, 1);

        userRoles.setId(id);
 
        when(userRolesRepository.existsById(id)).thenReturn(true);
 
        UserRoleOperationException exception = assertThrows(UserRoleOperationException.class, () -> {

            userRolesService.assignUserRole(userRoles);

        });
 
        assertEquals("ADDFAILS", exception.getCode());

        assertEquals("UserRole already exists", exception.getMessage());

        verify(userRolesRepository, never()).save(any());

    }
 
    // Test for getAllRoles

    @Test

    public void testGetAllRoles_Success() {

        List<UserRoleProjection> mockRoles = List.of(mock(UserRoleProjection.class));

        when(userRoleRepository.findAllProjectedBy()).thenReturn(mockRoles);
 
        List<UserRoleProjection> result = userRolesService.getAllRoles();
 
        assertNotNull(result);

        assertEquals(1, result.size());

    }
 
    // Test for getUserRolesByUserId

    @Test

    public void testGetUserRolesByUserId_Success() {

        int userId = 1;

        List<UserRoleProjection> mockRoles = List.of(mock(UserRoleProjection.class));

        when(userRolesRepository.findRolesByUserId(userId)).thenReturn(mockRoles);
 
        List<UserRoleProjection> result = userRolesService.getUserRolesByUserId(userId);
 
        assertNotNull(result);

        assertEquals(1, result.size());

    }
 
    @Test

    public void testGetUserRolesByUserId_NoRolesFound() {

        int userId = 1;

        when(userRolesRepository.findRolesByUserId(userId)).thenReturn(Collections.emptyList());
 
        UserRoleOperationException exception = assertThrows(UserRoleOperationException.class, () -> {

            userRolesService.getUserRolesByUserId(userId);

        });
 
        assertEquals("GETFAILS", exception.getCode());

        assertEquals("UserRole doesn't exist for user ID: " + userId, exception.getMessage());

    }
 
    // Test for revokeUserRole

    @Test

    public void testRevokeUserRole_Success() {

        int userRoleId = 1;

        int userId = 1;

        UserRolesId id = new UserRolesId(userId, userRoleId);

        UserRoles userRoles = new UserRoles();

        userRoles.setId(id);
 
        when(userRolesRepository.findById(id)).thenReturn(Optional.of(userRoles));
 
        Map<String, String> result = userRolesService.revokeUserRole(userRoleId, userId);
 
        assertEquals("DELETESUCCESS", result.get("code"));

        assertEquals("UserRole deleted successfully", result.get("message"));

        verify(userRolesRepository, times(1)).deleteById(id);

    }
 
    @Test

    public void testRevokeUserRole_UserRoleNotFound() {

        int userRoleId = 1;

        int userId = 1;

        UserRolesId id = new UserRolesId(userId, userRoleId);
 
        when(userRolesRepository.findById(id)).thenReturn(Optional.empty());
 
        UserRoleOperationException exception = assertThrows(UserRoleOperationException.class, () -> {

            userRolesService.revokeUserRole(userRoleId, userId);

        });
 
        assertEquals("DLTFAILS", exception.getCode());

        assertEquals("UserRole doesn't exist for the given ID", exception.getMessage());

    }

}

 