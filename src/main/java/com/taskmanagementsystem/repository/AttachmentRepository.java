package com.taskmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.taskmanagementsystem.dto.AttachmentProjection;
import com.taskmanagementsystem.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {
	
	@Query("SELECT a.attachmentId AS attachmentId, a.fileName AS fileName, a.filePath AS filePath, a.task.taskId AS taskId " +
	           "FROM Attachment a WHERE a.attachmentId = :attachmentId")
	    Optional<AttachmentProjection> findAttachmentById(int attachmentId);
	
	 @Query("SELECT a.attachmentId AS attachmentId, a.fileName AS fileName, a.filePath AS filePath, a.task.taskId AS taskId " +
	           "FROM Attachment a")
	    List<AttachmentProjection> findAllAttachments();
	 
	 @Query("SELECT a.attachmentId AS attachmentId, a.fileName AS fileName, a.filePath AS filePath, a.task.taskId AS taskId " +
	           "FROM Attachment a WHERE a.task.user.userId = :userId")
	 List<AttachmentProjection> findAttachmentsByUserId(int userId);
}
