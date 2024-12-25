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

import com.taskmanagementsystem.dto.AttachmentProjection;
import com.taskmanagementsystem.entity.Attachment;
import com.taskmanagementsystem.service.AttachmentService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    
    @PostMapping("/post")
    public ResponseEntity<String> uploadNewAttachment(@RequestBody Attachment attachment) {
        String response = attachmentService.createNewAttachment(attachment);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AttachmentProjection>> getListOfAllAttachment(){
    	
    	List<AttachmentProjection> listOfAllAttachment = attachmentService.getListOfAllAttachment();
    	return new ResponseEntity<>(listOfAllAttachment,HttpStatus.OK);
    }
    
    
    @GetMapping("/{attachmentId}")
    public ResponseEntity<AttachmentProjection> getAttachmentById(@PathVariable int attachmentId) {
        AttachmentProjection attachment = attachmentService.getAttachmentById(attachmentId);
        return new ResponseEntity<>(attachment,HttpStatus.OK);
    }
    
    @PutMapping("/update/{attachmentId}")
    public ResponseEntity<Map<String, String>> updateAttachmentDetails(
            @PathVariable int attachmentId,
            @RequestBody Attachment updatedAttachment) {

        Map<String, String> response = attachmentService.updateAttachmentDetails(attachmentId, updatedAttachment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{attachmentId}")
    public ResponseEntity<Map<String, String>> deleteAttachment(@PathVariable int attachmentId) {
        Map<String, String> response = attachmentService.deleteAttachment(attachmentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
