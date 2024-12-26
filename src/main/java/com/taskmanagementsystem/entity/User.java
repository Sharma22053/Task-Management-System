package com.taskmanagementsystem.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="queueId")
@Entity				                                  /*Make this class a JPA entity*/
@Table(name = "user")
public class User {

	@Id
	@Column(name = "UserID")
	private int userId;
	
	@Column(name = "Username")
	private String username;
	
	@Column(name = "Password")
	private String password;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "full_name")
	private String fullName;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Tasks> task;
	
	@OneToMany(mappedBy = "user")
	private List<Notification> notification;
	
	@OneToMany(mappedBy = "user")
	private List<Project> project;
	
	@OneToMany(mappedBy = "user")
	private List<Comment> comment;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "userroles",
        joinColumns = @JoinColumn(name = "UserID"),
        inverseJoinColumns = @JoinColumn(name = "UserRoleID")
    )
    private List<UserRole> roles;

	public User() { }    /*No args constructor is used by JPA to load data from the database*/

	public User(int userId, String username, String password, String email, String fullName) {
		
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
	}

	/*Getters and Setters*/
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<Tasks> getTask() {
		return task;
	}

	public void setTask(List<Tasks> task) {
		this.task = task;
	}

	public List<Notification> getNotification() {
		return notification;
	}

	public void setNotification(List<Notification> notification) {
		this.notification = notification;
	}

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	
	
}


