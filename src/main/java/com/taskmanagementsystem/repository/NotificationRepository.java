package com.taskmanagementsystem.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.taskmanagementsystem.dto.NotificationProjection;
import com.taskmanagementsystem.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Integer>{
	
	@Query("SELECT n.notificationId AS notificationId, n.text AS text, n.createdAt AS createdAt, n.user.userId AS userId " +
		       "FROM Notification n WHERE n.notificationId = :notificationId")
		Optional<NotificationProjection> findNotificationById(@Param("notificationId") int notificationId);
	
	@Query("SELECT n.notificationId AS notificationId, n.text AS text, n.createdAt AS createdAt, n.user.userId AS userId " +
		       "FROM Notification n")
		List<NotificationProjection> findAllNotifications();
	
	@Query("SELECT n.notificationId AS notificationId, n.text AS text, n.createdAt AS createdAt, n.user.userId AS userId " +
		       "FROM Notification n WHERE n.notificationId = :notificationId")
		Optional<NotificationProjection> getNotificationById(@Param("notificationId") int notificationId);
		
	
	@Query("SELECT n.notificationId as notificationId, n.text as text, n.createdAt as createdAt, n.user.userId as userId FROM Notification n WHERE n.user.userId = :userId")
	List<NotificationProjection> findNotificationsByUserId(int userId);
	
	
}
