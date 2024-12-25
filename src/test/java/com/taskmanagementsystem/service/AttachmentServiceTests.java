package com.taskmanagementsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taskmanagementsystem.dto.AttachmentProjection;
import com.taskmanagementsystem.entity.Attachment;
import com.taskmanagementsystem.entity.Tasks;
import com.taskmanagementsystem.exception.AttachmentOperationException;
import com.taskmanagementsystem.exception.TasksOperationException;
import com.taskmanagementsystem.repository.AttachmentRepository;
import com.taskmanagementsystem.repository.TasksRepository;

class AttachmentServiceTests {

    @InjectMocks
    private AttachmentService attachmentService;

    @Mock
    private AttachmentRepository attachmentRepository;

    @Mock
    private TasksRepository tasksRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewAttachment_shouldThrowException_whenTaskNotFound() {
        Attachment attachment = new Attachment();
        Tasks task = new Tasks();
        task.setTaskId(1);
        attachment.setTask(task);

        when(tasksRepository.findById(1)).thenReturn(Optional.empty());

        TasksOperationException exception = assertThrows(TasksOperationException.class, () -> 
            attachmentService.createNewAttachment(attachment)
        );

        assertEquals("TASK_NOT_FOUND", exception.getCode());
        assertEquals("Task with the given ID doesn't exist.", exception.getMessage());
    }

    @Test
    void createNewAttachment_shouldReturnSuccessMessage_whenAttachmentCreated() {
        Attachment attachment = new Attachment();
        Tasks task = new Tasks();
        task.setTaskId(1);
        attachment.setTask(task);

        when(tasksRepository.findById(1)).thenReturn(Optional.of(task));
        when(attachmentRepository.save(attachment)).thenReturn(attachment);

        String response = attachmentService.createNewAttachment(attachment);

        assertEquals("Attachment created successfully", response);
    }

    @Test
    void getListOfAllAttachment_shouldThrowException_whenNoAttachmentsFound() {
        when(attachmentRepository.findAllAttachments()).thenReturn(Collections.emptyList());

        AttachmentOperationException exception = assertThrows(AttachmentOperationException.class, () -> 
            attachmentService.getListOfAllAttachment()
        );

        assertEquals("GETFAILS", exception.getCode());
        assertEquals("No attachments found.", exception.getMessage());
    }

    @Test
    void getListOfAllAttachment_shouldReturnAttachmentList_whenAttachmentsExist() {
        AttachmentProjection mockProjection = mock(AttachmentProjection.class);
        when(attachmentRepository.findAllAttachments()).thenReturn(List.of(mockProjection));

        List<AttachmentProjection> attachments = attachmentService.getListOfAllAttachment();

        assertEquals(1, attachments.size());
        assertSame(mockProjection, attachments.get(0));
    }

    @Test
    void getAttachmentById_shouldThrowException_whenAttachmentNotFound() {
        when(attachmentRepository.findAttachmentById(1)).thenReturn(Optional.empty());

        AttachmentOperationException exception = assertThrows(AttachmentOperationException.class, () -> 
            attachmentService.getAttachmentById(1)
        );

        assertEquals("GETFAILS", exception.getCode());
        assertEquals("Attachment doesn't exist.", exception.getMessage());
    }

    @Test
    void getAttachmentById_shouldReturnAttachmentProjection_whenAttachmentExists() {
        AttachmentProjection mockProjection = mock(AttachmentProjection.class);
        when(attachmentRepository.findAttachmentById(1)).thenReturn(Optional.of(mockProjection));

        AttachmentProjection attachment = attachmentService.getAttachmentById(1);

        assertSame(mockProjection, attachment);
    }

    @Test
    void updateAttachmentDetails_shouldThrowException_whenAttachmentNotFound() {
        when(attachmentRepository.findById(1)).thenReturn(Optional.empty());

        Attachment updatedAttachment = new Attachment();

        AttachmentOperationException exception = assertThrows(AttachmentOperationException.class, () -> 
            attachmentService.updateAttachmentDetails(1, updatedAttachment)
        );

        assertEquals("UPDTFAILS", exception.getCode());
        assertEquals("Attachment doesn't exist.", exception.getMessage());
    }

    @Test
    void updateAttachmentDetails_shouldReturnSuccessResponse_whenAttachmentUpdated() {
        Attachment existingAttachment = new Attachment();
        Attachment updatedAttachment = new Attachment();
        updatedAttachment.setFileName("New File Name");
        updatedAttachment.setFilePath("/new/path");

        when(attachmentRepository.findById(1)).thenReturn(Optional.of(existingAttachment));
        when(attachmentRepository.save(existingAttachment)).thenReturn(existingAttachment);

        Map<String, String> response = attachmentService.updateAttachmentDetails(1, updatedAttachment);

        assertEquals("UPDATESUCCESS", response.get("code"));
        assertEquals("Attachment updated successfully", response.get("message"));
    }

    @Test
    void deleteAttachment_shouldThrowException_whenAttachmentNotFound() {
        when(attachmentRepository.findById(1)).thenReturn(Optional.empty());

        AttachmentOperationException exception = assertThrows(AttachmentOperationException.class, () -> 
            attachmentService.deleteAttachment(1)
        );

        assertEquals("DELFAILS", exception.getCode());
        assertEquals("Attachment doesn't exist.", exception.getMessage());
    }

    @Test
    void deleteAttachment_shouldReturnSuccessResponse_whenAttachmentDeleted() {
        Attachment existingAttachment = new Attachment();
        when(attachmentRepository.findById(1)).thenReturn(Optional.of(existingAttachment));
        doNothing().when(attachmentRepository).delete(existingAttachment);

        Map<String, String> response = attachmentService.deleteAttachment(1);

        assertEquals("DELSUCCESS", response.get("code"));
        assertEquals("Attachment deleted successfully", response.get("message"));
    }
}
