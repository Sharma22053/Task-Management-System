package com.taskmanagementsystem.entity;

import java.util.Date;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "task") 
public class Tasks {
	
	@Id
	@Column(name = "TaskID")
	private int taskId;

	@Column(name = "TaskName")
	private String taskName;

	@Column(name = "Description")
	private String description;

	@Temporal(TemporalType.DATE)   /*map date types to DB specific*/
	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "Priority")
	private String priority;

	@Column(name = "status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "ProjectID",nullable=false)
	private Project project;

	@ManyToOne
	@JoinColumn(name = "UserID",nullable=false)
	private User user;


	@OneToMany(mappedBy= "task")
	private List<Comment> comment;
	
	@ManyToMany
	@JoinTable(
			name = "taskcategory",
			joinColumns = @JoinColumn(name = "TaskID"),
			inverseJoinColumns = @JoinColumn(name = "CategoryID")
			)
	private List<Category> categories;
	
	@OneToMany(mappedBy= "task")
	private List<Attachment> attachment;

	  
	public Tasks() { }

	public Tasks(int taskId, String taskName, String description, Date dueDate, String priority, String status,
			User user,Project project) {

		this.taskId = taskId;
		this.taskName = taskName;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = status;
		this.user = user;
		this.project = project;

	}

	//Getters and Setters
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Attachment> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<Attachment> attachment) {
		this.attachment = attachment;
	}
	
	
	
}

