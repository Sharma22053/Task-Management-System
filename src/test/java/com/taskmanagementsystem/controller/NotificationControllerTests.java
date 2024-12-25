package com.taskmanagementsystem.controller;
 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.taskmanagementsystem.dto.NotificationProjection;
import com.taskmanagementsystem.entity.Notification;
import com.taskmanagementsystem.service.NotificationService;
 
@ExtendWith(MockitoExtension.class) // Enables Mockito support for JUnit 5

@WebMvcTest(NotificationController.class) // Specifies the controller to test

public class NotificationControllerTests {
 
    @InjectMocks

    private NotificationController notificationController; // Inject the controller to be tested
 
    @MockBean // This mocks the NotificationService bean for the test context

    private NotificationService notificationService; // Mock the NotificationService dependency
 
    private MockMvc mockMvc; // MockMvc to simulate HTTP requests
 
    @BeforeEach

    void setUp() {

        // Set up the MockMvc object

        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();

    }
 
    // Test for creating a new notification (POST /api/notifications/post)

    @Test

    void testCreateNewNotification() throws Exception {

        Notification notification = new Notification(1, "Test Notification", new Date(), null);

        // Create a mocked NotificationProjection object

        NotificationProjection notificationProjection = new NotificationProjection() {

            @Override

            public int getNotificationId() {

                return notification.getNotificationId();

            }
 
            @Override

            public String getText() {

                return notification.getText();

            }
 
            @Override

            public Date getCreatedAt() {

                return notification.getCreatedAt();

            }
 
            @Override

            public Integer getUserId() {

                return (notification.getUser() != null) ? notification.getUser().getUserId() : null;

            }

        };
 
        when(notificationService.createNewNotification(any(Notification.class))).thenReturn(notificationProjection);
 
        mockMvc.perform(post("/api/notifications/post")

                .contentType("application/json")

                .content("{\"notificationId\":1,\"text\":\"Test Notification\",\"createdAt\":\"2024-12-17T10:15:30.00Z\",\"userId\":null}"))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.notificationId").value(1))

                .andExpect(jsonPath("$.text").value("Test Notification"));

    }

    // 2. Test for getting list of all notifications (GET /api/notifications/all)

    @Test

    void testGetListOfAllNotifications() throws Exception {

        List<NotificationProjection> notifications = new ArrayList<>();

        Notification notification1 = new Notification(1, "Test Notification 1", new Date(), null);

        Notification notification2 = new Notification(2, "Test Notification 2", new Date(), null);

        notifications.add(new NotificationProjection() {

            @Override

            public int getNotificationId() {

                return notification1.getNotificationId();

            }
 
            @Override

            public String getText() {

                return notification1.getText();

            }
 
            @Override

            public Date getCreatedAt() {

                return notification1.getCreatedAt();

            }
 
            @Override

            public Integer getUserId() {

                return (notification1.getUser() != null) ? notification1.getUser().getUserId() : null;

            }

        });

        notifications.add(new NotificationProjection() {

            @Override

            public int getNotificationId() {

                return notification2.getNotificationId();

            }
 
            @Override

            public String getText() {

                return notification2.getText();

            }
 
            @Override

            public Date getCreatedAt() {

                return notification2.getCreatedAt();

            }
 
            @Override

            public Integer getUserId() {

                return (notification2.getUser() != null) ? notification2.getUser().getUserId() : null;

            }

        });
 
        when(notificationService.getListOfAllNotifications()).thenReturn(notifications);
 
        mockMvc.perform(get("/api/notifications/all"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].notificationId").value(1))

                .andExpect(jsonPath("$[0].text").value("Test Notification 1"))

                .andExpect(jsonPath("$[1].notificationId").value(2))

                .andExpect(jsonPath("$[1].text").value("Test Notification 2"));

    }

// 3. Test for getting notification by ID (GET /api/notifications/{notificationId})

    @Test

    void testGetNotificationById() throws Exception {

        Notification notification = new Notification(1, "Test Notification", new Date(), null);

        // Mocked NotificationProjection response

        NotificationProjection notificationProjection = new NotificationProjection() {

            @Override

            public int getNotificationId() {

                return notification.getNotificationId();

            }
 
            @Override

            public String getText() {

                return notification.getText();

            }
 
            @Override

            public Date getCreatedAt() {

                return notification.getCreatedAt();

            }
 
            @Override

            public Integer getUserId() {

                return (notification.getUser() != null) ? notification.getUser().getUserId() : null;

            }

        };
 
        when(notificationService.getNotificationById(1)).thenReturn(notificationProjection);
 
        mockMvc.perform(get("/api/notifications/{notificationId}", 1))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.notificationId").value(1))

                .andExpect(jsonPath("$.text").value("Test Notification"));

    }

    // 4. Test for updating notification details (PUT /api/notifications/update/{notificationId})

    @Test

    void testUpdateNotificationDetails() throws Exception {

        // Setup mock notification details

        Notification updatedNotification = new Notification(1, "Updated Notification", new Date(), null);
 
        // Prepare the expected response with the correct type

        Map<String, Object> successResponse = new HashMap<>();

        successResponse.put("message", "Notification updated successfully.");

        // Mock service call

        when(notificationService.updateNotificationDetails(eq(1), any(Notification.class)))

                   .thenReturn(successResponse);
 
        // Perform PUT request

        mockMvc.perform(MockMvcRequestBuilders.put("/api/notifications/update/{notificationId}", 1)

                       .contentType("application/json")

                       .content("{\"notificationId\":1, \"text\":\"Updated Notification\", \"createdAt\":\"2024-12-17T10:15:30.00Z\"}"))

                       .andExpect(status().isCreated())  // Expect 201 Created (instead of 200 OK)

                       .andExpect(jsonPath("$.message").value("Notification updated successfully."));  // Verify the message field

    }
 
 
    // 5. Test for deleting a notification (DELETE /api/notifications/delete/{notificationId})

    @Test

    void testDeleteNotification() throws Exception {

        Map<String, String> successResponse = Map.of("message", "Notification deleted successfully");
 
        when(notificationService.deleteNotification(1)).thenReturn(successResponse);
 
        mockMvc.perform(delete("/api/notifications/delete/{notificationId}", 1))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.message").value("Notification deleted successfully"));

    }
 
}

 