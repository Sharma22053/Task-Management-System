package com.taskmanagementsystem.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanagementsystem.dto.AttachmentProjection;
import com.taskmanagementsystem.entity.Attachment;
import com.taskmanagementsystem.entity.Tasks;
import com.taskmanagementsystem.exception.AttachmentOperationException;
import com.taskmanagementsystem.exception.TasksOperationException;
import com.taskmanagementsystem.repository.AttachmentRepository;
import com.taskmanagementsystem.repository.TasksRepository;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;
    
    @Autowired
    private TasksRepository tasksRepository; 
    
    
    public String createNewAttachment(Attachment attachment) {
        
        Optional<Tasks> taskOptional = tasksRepository.findById(attachment.getTask().getTaskId());
        if (!taskOptional.isPresent()) {
            throw new TasksOperationException("TASK_NOT_FOUND", "Task with the given ID doesn't exist.");
        }
         attachmentRepository.save(attachment);
         return "Attachment created successfully";
    }
    
    public List<AttachmentProjection> getListOfAllAttachment() {
        List<AttachmentProjection> listOfAllAttachments = attachmentRepository.findAllAttachments();
        if (listOfAllAttachments.isEmpty()) {
            throw new AttachmentOperationException("GETFAILS", "No attachments found.");
        }
        return listOfAllAttachments;
    }

    public AttachmentProjection getAttachmentById(int attachmentId) {
        Optional<AttachmentProjection> attachmentOptional = attachmentRepository.findAttachmentById(attachmentId);
        if (!attachmentOptional.isPresent()) {
            throw new AttachmentOperationException("GETFAILS", "Attachment doesn't exist.");
        }
        return attachmentOptional.get();
    }
    
    public Map<String, String> updateAttachmentDetails(int attachmentId, Attachment updatedAttachment) {

        Attachment existingAttachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new AttachmentOperationException("UPDTFAILS", "Attachment doesn't exist."));

       
        existingAttachment.setFileName(updatedAttachment.getFileName());
        existingAttachment.setFilePath(updatedAttachment.getFilePath());
        if (updatedAttachment.getTask() != null) {
            existingAttachment.setTask(updatedAttachment.getTask());
        }

        // Save the updated attachment
        attachmentRepository.save(existingAttachment);

        // Prepare and return success response
        Map<String, String> response = new LinkedHashMap<>();
        response.put("code", "UPDATESUCCESS");
        response.put("message", "Attachment updated successfully");
        return response;
    }

    public Map<String, String> deleteAttachment(int attachmentId) {
     
        Attachment existingAttachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new AttachmentOperationException("DELFAILS", "Attachment doesn't exist."));
      
        attachmentRepository.delete(existingAttachment);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("code", "DELSUCCESS");
        response.put("message", "Attachment deleted successfully");
        return response;
    }
}
