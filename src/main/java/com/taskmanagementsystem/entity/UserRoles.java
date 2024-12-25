package com.taskmanagementsystem.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "userroles")
public class UserRoles {

    @EmbeddedId
    private UserRolesId id;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "UserRoleID", insertable = false, updatable = false)
    private UserRole userRole;

    public UserRoles() {}

    public UserRoles(UserRolesId id, User user, UserRole userRole) {
        this.id = id;
        this.user = user;
        this.userRole = userRole;
    }

    public UserRolesId getId() {
        return id;
    }

    public void setId(UserRolesId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
