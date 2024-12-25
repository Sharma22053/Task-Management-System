package com.taskmanagementsystem.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagementsystem.dto.NotificationProjection;
import com.taskmanagementsystem.entity.Notification;
import com.taskmanagementsystem.service.NotificationService;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/post")
	public ResponseEntity<NotificationProjection> createNewNotification(@RequestBody Notification notification){
		
		NotificationProjection savedNotification = notificationService.createNewNotification(notification);
	    return new ResponseEntity<>(savedNotification, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<NotificationProjection>> getListOfAllNotifications(){
		
		List<NotificationProjection> listAllNotications = notificationService.getListOfAllNotifications();
		return new ResponseEntity<>(listAllNotications,HttpStatus.OK);
	}
	
	@GetMapping("/{notificationId}")
	public ResponseEntity<NotificationProjection> getNotificationById(@PathVariable int notificationId){
		
		NotificationProjection notificationDetails = notificationService.getNotificationById(notificationId);
		return new ResponseEntity<>(notificationDetails,HttpStatus.OK);
	}
	
	@PutMapping("/update/{notificationId}")
	public ResponseEntity<Map<String, Object>> updateNotificationDetails(
	        @PathVariable int notificationId,
	        @RequestBody Notification updatedNotificationDetails) {

	    Map<String,Object> response =  notificationService.updateNotificationDetails(notificationId, updatedNotificationDetails);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{notificationId}")
	public ResponseEntity<Map<String,String>> deleteNotification(@PathVariable int notificationId){
		
		Map<String,String> successResponse = notificationService.deleteNotification(notificationId);
		return new ResponseEntity<>(successResponse,HttpStatus.OK);
	}
}
