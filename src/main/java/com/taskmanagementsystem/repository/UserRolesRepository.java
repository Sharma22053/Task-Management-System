package com.taskmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.taskmanagementsystem.dto.UserRoleProjection;
import com.taskmanagementsystem.entity.UserRoles;
import com.taskmanagementsystem.entity.UserRolesId;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UserRolesId> {

    List<UserRoles> findByIdUserId(int userId);

    boolean existsById(UserRolesId id);
    
    @Query("SELECT ur.userRole.userRoleId AS userRoleId, ur.userRole.roleName AS roleName " +
            "FROM UserRoles ur WHERE ur.user.userId = :userId")
     List<UserRoleProjection> findRolesByUserId(int userId);
}
