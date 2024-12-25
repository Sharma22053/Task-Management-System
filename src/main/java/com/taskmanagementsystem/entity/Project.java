package com.taskmanagementsystem.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="project")
public class Project {

	@Id
	@Column(name="ProjectID")
	private int projectId;
	
	
	@Column(name="ProjectName")
	@NotBlank(message = "Task Name is mandatory")
	private String projectName;
	
	@Column(name="Description")
	private String description;
	
	
	@Column(name="StartDate")
	private Date startDate;
	
	
	@Column(name="EndDate")
	private Date endDate;
	
	
	@ManyToOne
	@JoinColumn(name="UserID")
	private User user;

	@OneToMany(mappedBy="project", cascade = CascadeType.ALL)
	private List<Tasks> tasks;
	
	public Project() {}
	

	public Project(int projectId, @NotBlank(message = "Task Name is mandatory") String projectName, String description,
			Date startDate, Date endDate, User user) {
		
		this.projectId = projectId;
		this.projectName = projectName;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;	
		this.user = user;
	}


	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Tasks> getTasks() {
		return tasks;
	}

	public void setTasks(List<Tasks> tasks) {
		this.tasks = tasks;
	}
	
	
}
