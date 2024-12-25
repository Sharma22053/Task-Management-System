package com.taskmanagementsystem.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification{
	
	@Id
	@Column(name = "NotificationId")
	private int notificationId;
	@Column(name = "Text")
	private String text;
	@Column(name = "CreatedAt")
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name = "UserID",nullable=false)
	private User user;
	
	
	public Notification() { }

	public Notification(int notificationId, String text, Date createdAt,User user) {
		
		this.notificationId = notificationId;
		this.text = text;
		this.createdAt = createdAt;
		this.user = user;
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}

