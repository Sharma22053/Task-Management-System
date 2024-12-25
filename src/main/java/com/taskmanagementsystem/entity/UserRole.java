package com.taskmanagementsystem.entity;

import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "userrole")
public class UserRole {
	
	@Id
	@Column(name= "UserRoleID")
	private int userRoleId;
	
	@Column(name= "RoleName")
	private String roleName;
	
	@ManyToMany(mappedBy = "roles")
    private List<User> users;

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/*didn't want to make a projection that's why these methods are comment out*/
//	public List<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}
	
	public UserRole() {}

	public UserRole(int userRoleId, String roleName) {
		this.userRoleId = userRoleId;
		this.roleName = roleName;
	}
	
	
}