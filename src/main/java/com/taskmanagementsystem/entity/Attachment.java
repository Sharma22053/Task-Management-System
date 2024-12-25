package com.taskmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attachment")

public class Attachment {
	
	@Id
	@Column(name= "AttachmentID")
	private int attachmentId;
	
	@Column(name= "FileName")
	private String fileName;
	
	@Column(name= "FilePath")
	private String filePath;
	
	@ManyToOne
	@JoinColumn(name = "TaskID", nullable = false)
	private Tasks task;
	
	public Attachment() {}

	public Attachment(int attachmentId, String fileName, String filePath, Tasks task) {
		
		this.attachmentId = attachmentId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.task = task;
	}

	public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Tasks getTask() {
		return task;
	}

	public void setTask(Tasks task) {
		this.task = task;
	}
	
	
}
