package com.taskmanagementsystem.entity;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@Column(name = "CommentID")
	private int commentId;

	@Column(name = "Text")
	private String text;

	@Column(name = "CreatedAt")
	private Date createdAt;

	@ManyToOne
	@JoinColumn(name = "UserID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "TaskID")
	private Tasks task;
	
	public Comment() { }

	public Comment(int commentId, String text, Date createdAt, User user, Tasks task) {
		
		this.commentId = commentId;
		this.text = text;
		this.createdAt = createdAt;
		this.user = user;
		this.task = task;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tasks getTask() {
		return task;
	}

	public void setTask(Tasks task) {
		this.task = task;
	}
	
}