package com.taskmanagementsystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagementsystem.dto.AttachmentProjection;
import com.taskmanagementsystem.entity.Attachment;
import com.taskmanagementsystem.service.AttachmentService;

@WebMvcTest(AttachmentController.class)
class AttachmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttachmentService attachmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUploadNewAttachment() throws Exception {
        Attachment attachment = new Attachment(); // Add required fields
        attachment.setAttachmentId(1);
        attachment.setFileName("sample.txt");
        attachment.setFilePath("/files/sample.txt");
       

        when(attachmentService.createNewAttachment(Mockito.any(Attachment.class))).thenReturn("Attachment created successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/attachments/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(attachment)))
                .andExpect(status().isOk())
                .andExpect(content().string("Attachment created successfully"));

        verify(attachmentService, times(1)).createNewAttachment(Mockito.any(Attachment.class));
    }

    @Test
    void testGetListOfAllAttachment() throws Exception {
        AttachmentProjection projection = mock(AttachmentProjection.class);
        when(projection.getAttachmentId()).thenReturn(1);
        when(projection.getFileName()).thenReturn("sample.txt");
        when(projection.getFilePath()).thenReturn("/files/sample.txt");
        when(projection.getTaskId()).thenReturn(101);

        List<AttachmentProjection> projections = List.of(projection);

        when(attachmentService.getListOfAllAttachment()).thenReturn(projections);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/attachments/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].attachmentId").value(1))
                .andExpect(jsonPath("$[0].fileName").value("sample.txt"))
                .andExpect(jsonPath("$[0].filePath").value("/files/sample.txt"))
                .andExpect(jsonPath("$[0].taskId").value(101));

        verify(attachmentService, times(1)).getListOfAllAttachment();
    }

    @Test
    void testGetAttachmentById() throws Exception {
        AttachmentProjection projection = mock(AttachmentProjection.class);
        when(projection.getAttachmentId()).thenReturn(1);
        when(projection.getFileName()).thenReturn("sample.txt");
        when(projection.getFilePath()).thenReturn("/files/sample.txt");
        when(projection.getTaskId()).thenReturn(101);

        when(attachmentService.getAttachmentById(1)).thenReturn(projection);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/attachments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attachmentId").value(1))
                .andExpect(jsonPath("$.fileName").value("sample.txt"))
                .andExpect(jsonPath("$.filePath").value("/files/sample.txt"))
                .andExpect(jsonPath("$.taskId").value(101));

        verify(attachmentService, times(1)).getAttachmentById(1);
    }

    @Test
    void testUpdateAttachmentDetails() throws Exception {
        Attachment updatedAttachment = new Attachment(); // Add required fields
        updatedAttachment.setAttachmentId(1);
        updatedAttachment.setFileName("updated.txt");
        updatedAttachment.setFilePath("/files/updated.txt");
       

        Map<String, String> response = Map.of("message", "Attachment updated successfully");

        when(attachmentService.updateAttachmentDetails(eq(1), any(Attachment.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/attachments/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAttachment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Attachment updated successfully"));

        verify(attachmentService, times(1)).updateAttachmentDetails(eq(1), any(Attachment.class));
    }

    @Test
    void testDeleteAttachment() throws Exception {
        Map<String, String> response = Map.of("message", "Attachment deleted successfully");

        when(attachmentService.deleteAttachment(1)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/attachments/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Attachment deleted successfully"));

        verify(attachmentService, times(1)).deleteAttachment(1);
    }
}
