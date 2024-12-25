package com.taskmanagementsystem.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanagementsystem.dto.NotificationProjection;
import com.taskmanagementsystem.entity.Notification;
import com.taskmanagementsystem.exception.NotificationOperationException;
import com.taskmanagementsystem.repository.NotificationRepository;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Transactional
	public NotificationProjection createNewNotification(Notification notification) {
	    if (notification == null || notification.getText() == null || notification.getText().isEmpty()) {
	        throw new NotificationOperationException("ADDFAILS", "Notification text cannot be null or empty");
	    }

	    Notification savedNotification = notificationRepository.save(notification);

	    return notificationRepository.findNotificationById(savedNotification.getNotificationId())
	            .orElseThrow(() -> new NotificationOperationException("CREATEFAILS", "Failed to fetch the saved notification"));
	}

	public List<NotificationProjection> getListOfAllNotifications() {
		
		List<NotificationProjection> listAllNotifications = notificationRepository.findAllNotifications();
		if(listAllNotifications.isEmpty()) {
			throw new NotificationOperationException("GETFAILS", "Notification list is empty");
		}
		
		return listAllNotifications;
	}

	public NotificationProjection getNotificationById(int notificationId) {
		
		return notificationRepository.getNotificationById(notificationId)
	            .orElseThrow(() -> new NotificationOperationException("GETFAILS", "Notification doesn't exist"));
	}

	@Transactional
	public Map<String,Object> updateNotificationDetails(int notificationId, Notification updatedNotificationDetails) {
	    
	    Notification existingNotification = notificationRepository.findById(notificationId)
	            .orElseThrow(() -> new NotificationOperationException("UPDTFAILS", "Notification doesn't exist"));

	    
	    existingNotification.setText(updatedNotificationDetails.getText());
	    existingNotification.setCreatedAt(updatedNotificationDetails.getCreatedAt());

	    
	    notificationRepository.save(existingNotification);
	    Map<String, Object> successResponse = new LinkedHashMap<>();
	    successResponse.put("code", "UPDATESUCCESS");
	    successResponse.put("message", "Notification updated successfully");
	    successResponse.put("Updated Data", notificationRepository.getNotificationById(notificationId));
	    
	    return successResponse;
	}

	@Transactional
	public Map<String,String> deleteNotification(int notificationId) {
		
		Notification existingNotification = notificationRepository.findById(notificationId)
	            .orElseThrow(() -> new NotificationOperationException("DLTFAILS", "Notification doesn't exist"));
		
		notificationRepository.deleteById(notificationId);
		Map<String, String> successResponse = new LinkedHashMap<>();
	    successResponse.put("code", "UPDATESUCCESS");
	    successResponse.put("message", "Notification deleted successfully");	   
	    return successResponse;
	}



}
