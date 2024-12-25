package com.taskmanagementsystem.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.taskmanagementsystem.entity.UserRole;
import com.taskmanagementsystem.exception.UserRoleOperationException;
import com.taskmanagementsystem.repository.UserRoleRepository;
import com.taskmanagementsystem.service.UserRoleService;
import java.util.Optional;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
 
@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTests {
    @Mock
    private UserRoleRepository userRoleRepository;
    @InjectMocks
    private UserRoleService userRoleService;
    private UserRole userRole;
    @BeforeEach
    public void setUp() {
        userRole = new UserRole();
        userRole.setUserRoleId(1);
        userRole.setRoleName("Admin");
    }  
    // Test for createNewUserRole
    @Test
    public void testCreateNewUserRole_Success() {
        when(userRoleRepository.findById(userRole.getUserRoleId())).thenReturn(Optional.empty());
        when(userRoleRepository.save(userRole)).thenReturn(userRole);
        Map<String, String> response = userRoleService.createNewUserRole(userRole);
        assertEquals("POSTSUCCESS", response.get("code"));
        assertEquals("UserRoles added successfully", response.get("message"));
    }
 
    @Test
    public void testCreateNewUserRole_AlreadyExists() {
        when(userRoleRepository.findById(userRole.getUserRoleId())).thenReturn(Optional.of(userRole));
        UserRoleOperationException exception = assertThrows(UserRoleOperationException.class, () ->
            userRoleService.createNewUserRole(userRole));
        assertEquals("ADDFAILS", exception.getCode());
        assertEquals("Userroles already exist", exception.getMessage());
    }
    
    // Test for getAllUserRole
    @Test
    public void testGetAllUserRole_Success() {
        when(userRoleRepository.findAll()).thenReturn(List.of(userRole));
        List<UserRole> result = userRoleService.getAllUserRole();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
 
    @Test
    public void testGetAllUserRole_EmptyList() {
        when(userRoleRepository.findAll()).thenReturn(List.of());
        UserRoleOperationException exception = assertThrows(UserRoleOperationException.class, () ->
            userRoleService.getAllUserRole());
        assertEquals("GETFAILS", exception.getCode());
        assertEquals("Userroles is empty", exception.getMessage());
    }
    
// Test for getDetailsByUserId
    @Test
    public void testGetDetailsByUserId_Success() {
        when(userRoleRepository.findById(userRole.getUserRoleId())).thenReturn(Optional.of(userRole));
        UserRole result = userRoleService.getDetailsByUserId(userRole.getUserRoleId());
        assertEquals(userRole.getUserRoleId(), result.getUserRoleId());
        assertEquals(userRole.getRoleName(), result.getRoleName());
    }
 
    @Test
    public void testGetDetailsByUserId_NotFound() {
        when(userRoleRepository.findById(userRole.getUserRoleId())).thenReturn(Optional.empty());
        UserRoleOperationException exception = assertThrows(UserRoleOperationException.class, () ->
            userRoleService.getDetailsByUserId(userRole.getUserRoleId()));
        assertEquals("GETFAILS", exception.getCode());
        assertEquals("User role doesn't exist", exception.getMessage());
    }
    
// Test for updateUserRole
    @Test
    public void testUpdateUserRole_Success() {
        UserRole updatedRole = new UserRole();
        updatedRole.setRoleName("Manager");
        when(userRoleRepository.findById(userRole.getUserRoleId())).thenReturn(Optional.of(userRole));
        when(userRoleRepository.save(userRole)).thenReturn(userRole);
        Map<String, String> response = userRoleService.updateUserRole(userRole.getUserRoleId(), updatedRole);
        assertEquals("UPDATESUCCESS", response.get("code"));
        assertEquals("USerrole updated successfully", response.get("status"));
    }
 
    @Test
    public void testUpdateUserRole_NotFound() {
        UserRole updatedRole = new UserRole();
        updatedRole.setRoleName("Manager");
        when(userRoleRepository.findById(userRole.getUserRoleId())).thenReturn(Optional.empty());
        UserRoleOperationException exception = assertThrows(UserRoleOperationException.class, () ->
            userRoleService.updateUserRole(userRole.getUserRoleId(), updatedRole));
        assertEquals("UPDATEFAIL", exception.getCode());
        assertEquals("UserRole does not exist", exception.getMessage());
    }
    // Test for deleteUserRole
    @Test
    public void testDeleteUserRole_Success() {
        when(userRoleRepository.findById(userRole.getUserRoleId())).thenReturn(Optional.of(userRole));
        Map<String, String> response = userRoleService.deleteUserRole(userRole.getUserRoleId());
        assertEquals("DELETESUCCESS", response.get("code"));
        assertEquals("UserRole deleted successfully", response.get("message"));
    }
    
 
    @Test
    public void testDeleteUserRole_NotFound() {
        when(userRoleRepository.findById(userRole.getUserRoleId())).thenReturn(Optional.empty());
        UserRoleOperationException exception = assertThrows(UserRoleOperationException.class, () ->
            userRoleService.deleteUserRole(userRole.getUserRoleId()));
        assertEquals("DELETEFAIL", exception.getCode());
        assertEquals("UserRole does not exist", exception.getMessage());
    }
 
}
 
 