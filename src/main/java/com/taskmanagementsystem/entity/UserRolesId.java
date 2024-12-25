package com.taskmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserRolesId implements Serializable {

    @Column(name = "UserID")
    private int userId;

    @Column(name = "UserRoleID")
    private int userRoleId;

    public UserRolesId() {}

    public UserRolesId(int userId, int userRoleId) {
        this.userId = userId;
        this.userRoleId = userRoleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRolesId that = (UserRolesId) o;
        return userId == that.userId && userRoleId == that.userRoleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userRoleId);
    }
}
