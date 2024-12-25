package com.taskmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagementsystem.dto.UserRoleProjection;
import com.taskmanagementsystem.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer>{

	List<UserRoleProjection> findAllProjectedBy();
}
