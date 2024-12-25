package com.taskmanagementsystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

@ExtendWith(MockitoExtension.class)
class RoleBasedLoginControllerTests {

    @InjectMocks
    private RoleBasedLoginController roleBasedLoginController;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProjectProjection mockProjectProjection;
    private TaskProjection mockTaskProjection;
    private NotificationProjection mockNotificationProjection;
    private CommentProjection mockCommentProjection;
    private AttachmentProjection mockAttachmentProjection;
    private CategoryProjection mockCategoryProjection;

    @BeforeEach
    void setUp() {
        mockProjectProjection = mock(ProjectProjection.class);
        mockTaskProjection = mock(TaskProjection.class);
        mockNotificationProjection = mock(NotificationProjection.class);
        mockCommentProjection = mock(CommentProjection.class);
        mockAttachmentProjection = mock(AttachmentProjection.class);
        mockCategoryProjection = mock(CategoryProjection.class);
    }

    @Test
    void testGetProjectsAssigned() {
        List<ProjectProjection> mockProjects = Arrays.asList(mockProjectProjection);
        when(projectRepository.findProjectsByUserId(1)).thenReturn(mockProjects);

        ResponseEntity<List<ProjectProjection>> response = roleBasedLoginController.getProjectsassigned(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void testGetTasksAssigned() {
        List<TaskProjection> mockTasks = Arrays.asList(mockTaskProjection);
        when(tasksRepository.findByUser_UserId(1)).thenReturn(mockTasks);

        ResponseEntity<List<TaskProjection>> response = roleBasedLoginController.getTasksAssigned(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void testGetNotificationsAssigned() {
        List<NotificationProjection> mockNotifications = Arrays.asList(mockNotificationProjection);
        when(notificationRepository.findNotificationsByUserId(1)).thenReturn(mockNotifications);

        ResponseEntity<List<NotificationProjection>> response = roleBasedLoginController.getNotificationsAssigned(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void testGetCommentsAssigned() {
        List<CommentProjection> mockComments = Arrays.asList(mockCommentProjection);
        when(commentRepository.findCommentsByUserId(1)).thenReturn(mockComments);

        ResponseEntity<List<CommentProjection>> response = roleBasedLoginController.getCommentsAssigned(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void testGetAttachmentsAssigned() {
        List<AttachmentProjection> mockAttachments = Arrays.asList(mockAttachmentProjection);
        when(attachmentRepository.findAttachmentsByUserId(1)).thenReturn(mockAttachments);

        ResponseEntity<List<AttachmentProjection>> response = roleBasedLoginController.getAttachmentsAssigned(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void testGetCategoriesAssigned() {
        List<CategoryProjection> mockCategories = Arrays.asList(mockCategoryProjection);
        when(categoryRepository.findCategoriesByUserId(1)).thenReturn(mockCategories);

        ResponseEntity<List<CategoryProjection>> response = roleBasedLoginController.getCategoriesAssigned(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    void testGetTasksAssigned_NoContent() {
        when(tasksRepository.findByUser_UserId(1)).thenReturn(Arrays.asList());

        ResponseEntity<List<TaskProjection>> response = roleBasedLoginController.getTasksAssigned(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetNotificationsAssigned_NoContent() {
        when(notificationRepository.findNotificationsByUserId(1)).thenReturn(Arrays.asList());

        ResponseEntity<List<NotificationProjection>> response = roleBasedLoginController.getNotificationsAssigned(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetCommentsAssigned_NoContent() {
        when(commentRepository.findCommentsByUserId(1)).thenReturn(Arrays.asList());

        ResponseEntity<List<CommentProjection>> response = roleBasedLoginController.getCommentsAssigned(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetAttachmentsAssigned_NoContent() {
        when(attachmentRepository.findAttachmentsByUserId(1)).thenReturn(Arrays.asList());

        ResponseEntity<List<AttachmentProjection>> response = roleBasedLoginController.getAttachmentsAssigned(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetCategoriesAssigned_NoContent() {
        when(categoryRepository.findCategoriesByUserId(1)).thenReturn(Arrays.asList());

        ResponseEntity<List<CategoryProjection>> response = roleBasedLoginController.getCategoriesAssigned(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
