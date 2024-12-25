package com.taskmanagementsystem.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.taskmanagementsystem.dto.NotificationProjection;
import com.taskmanagementsystem.entity.Notification;
import com.taskmanagementsystem.exception.NotificationOperationException;
import com.taskmanagementsystem.repository.NotificationRepository;
import com.taskmanagementsystem.service.NotificationService;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTests {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void createNewNotification_Success() {
        // Arrange
    	Notification notification = new Notification();
        notification.setNotificationId(1);
        notification.setText("Test Notification");
        notification.setCreatedAt(new Date());
        NotificationProjection mockProjection = mock(NotificationProjection.class);

        when(notificationRepository.save(notification)).thenReturn(notification);
        when(notificationRepository.findNotificationById(1)).thenReturn(Optional.of(mockProjection));

        // Act
        NotificationProjection result = notificationService.createNewNotification(notification);

        // Assert
        Assertions.assertNotNull(result);
        verify(notificationRepository).save(notification);
        verify(notificationRepository).findNotificationById(1);
    }

    @Test
    void createNewNotification_TextIsNullOrEmpty_ThrowsException() {
        // Arrange
        Notification invalidNotification = new Notification();

        invalidNotification.setNotificationId(1);
        invalidNotification.setText("");
        invalidNotification.setCreatedAt(new Date());

        // Act & Assert
        Assertions.assertThrows(NotificationOperationException.class, () ->
                notificationService.createNewNotification(invalidNotification));
    }

    @Test
    void getListOfAllNotifications_Success() {
        // Arrange
        NotificationProjection mockProjection = mock(NotificationProjection.class);
        when(notificationRepository.findAllNotifications()).thenReturn(List.of(mockProjection));

        // Act
        List<NotificationProjection> result = notificationService.getListOfAllNotifications();

        // Assert
        Assertions.assertFalse(result.isEmpty());
        verify(notificationRepository).findAllNotifications();
    }

    @Test
    void getListOfAllNotifications_EmptyList_ThrowsException() {
        // Arrange
        when(notificationRepository.findAllNotifications()).thenReturn(Collections.emptyList());

        // Act & Assert
        Assertions.assertThrows(NotificationOperationException.class, () ->
                notificationService.getListOfAllNotifications());
    }

    @Test
    void getNotificationById_Found_Success() {
        // Arrange
        NotificationProjection mockProjection = mock(NotificationProjection.class);
        when(notificationRepository.getNotificationById(1)).thenReturn(Optional.of(mockProjection));

        // Act
        NotificationProjection result = notificationService.getNotificationById(1);

        // Assert
        Assertions.assertNotNull(result);
        verify(notificationRepository).getNotificationById(1);
    }

    @Test
    void getNotificationById_NotFound_ThrowsException() {
        // Arrange
        when(notificationRepository.getNotificationById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotificationOperationException.class, () ->
                notificationService.getNotificationById(1));
    }

    @Test
    void updateNotificationDetails_Success() {
        // Arrange
        Notification existingNotification = new Notification();
   
        existingNotification.setNotificationId(1);
        existingNotification.setText("Old Text");
        existingNotification.setCreatedAt(new Date());
        
        Notification updatedNotification = new Notification();
       
        updatedNotification.setCreatedAt(new Date());
        updatedNotification.setText("Updated Text");
        updatedNotification.setNotificationId(1);
        NotificationProjection mockProjection = mock(NotificationProjection.class);

        when(notificationRepository.findById(1)).thenReturn(Optional.of(existingNotification));
        when(notificationRepository.getNotificationById(1)).thenReturn(Optional.of(mockProjection));

        // Act
        Map<String, Object> response = notificationService.updateNotificationDetails(1, updatedNotification);

        // Assert
        Assertions.assertEquals("UPDATESUCCESS", response.get("code"));
        Assertions.assertEquals("Notification updated successfully", response.get("message"));
        verify(notificationRepository).save(existingNotification);
    }

   

    @Test
    void deleteNotification_Success() {
        // Arrange
    	Notification existingNotification = new Notification();
    	   
        existingNotification.setNotificationId(1);
        existingNotification.setText("Old Text");
        existingNotification.setCreatedAt(new Date());
        when(notificationRepository.findById(1)).thenReturn(Optional.of(existingNotification));

        // Act
        Map<String, String> response = notificationService.deleteNotification(1);

        // Assert
        Assertions.assertEquals("UPDATESUCCESS", response.get("code"));
        Assertions.assertEquals("Notification deleted successfully", response.get("message"));
        verify(notificationRepository).deleteById(1);
    }

    @Test
    void deleteNotification_NotFound_ThrowsException() {
        // Arrange
        when(notificationRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotificationOperationException.class, () ->
                notificationService.deleteNotification(1));
    }
}
