package com.taskmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagementsystem.dto.AttachmentProjection;
import com.taskmanagementsystem.dto.CategoryProjection;
import com.taskmanagementsystem.dto.CommentProjection;
import com.taskmanagementsystem.dto.NotificationProjection;
import com.taskmanagementsystem.dto.ProjectProjection;
import com.taskmanagementsystem.dto.TaskProjection;
import com.taskmanagementsystem.repository.AttachmentRepository;
import com.taskmanagementsystem.repository.CategoryRepository;
import com.taskmanagementsystem.repository.CommentRepository;
import com.taskmanagementsystem.repository.NotificationRepository;
import com.taskmanagementsystem.repository.ProjectRepository;
import com.taskmanagementsystem.repository.TasksRepository;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/rbac")
public class RoleBasedLoginController {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	TasksRepository tasksRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("/project/{userId}")
	ResponseEntity<List<ProjectProjection>> getProjectsassigned(@PathVariable int userId){
		
		List<ProjectProjection> listProjects = projectRepository.findProjectsByUserId(userId);
		
		return new ResponseEntity<>(listProjects,HttpStatus.OK);
	}
	
	@GetMapping("/tasks/{userId}")
    public ResponseEntity<List<TaskProjection>> getTasksAssigned(@PathVariable int userId) {
        List<TaskProjection> listTasks = tasksRepository.findByUser_UserId(userId);
        if (listTasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listTasks);
    }
	
	@GetMapping("/notifications/{userId}")
	public ResponseEntity<List<NotificationProjection>> getNotificationsAssigned(@PathVariable int userId){
	    List<NotificationProjection> listNotification = notificationRepository.findNotificationsByUserId(userId);
	    if(listNotification.isEmpty()) {
	        return ResponseEntity.noContent().build();        
	    }
	    return ResponseEntity.ok(listNotification);
	}
	
	@GetMapping("/comment/{userId}")
	public ResponseEntity<List<CommentProjection>> getCommentsAssigned(@PathVariable int userId){
		
		List<CommentProjection> listComment = commentRepository.findCommentsByUserId(userId);
		if(listComment.isEmpty()) {
			 return ResponseEntity.noContent().build();        
	    }
	    return ResponseEntity.ok(listComment);
	}
	
	@GetMapping("/attachments/{userId}")
	public ResponseEntity<List<AttachmentProjection>> getAttachmentsAssigned(@PathVariable int userId){
		
		List<AttachmentProjection> listAttachment = attachmentRepository.findAttachmentsByUserId(userId);
		if(listAttachment.isEmpty()) {
			 return ResponseEntity.noContent().build();        
	    }
	    return ResponseEntity.ok(listAttachment);
	}
	
	@GetMapping("/category/{userId}")
	public ResponseEntity<List<CategoryProjection>> getCategoriesAssigned(@PathVariable int userId){
		
		List<CategoryProjection> listCategory = categoryRepository.findCategoriesByUserId(userId);
		if(listCategory.isEmpty()) {
			 return ResponseEntity.noContent().build();        
	    }
	    return ResponseEntity.ok(listCategory);
	}
}
