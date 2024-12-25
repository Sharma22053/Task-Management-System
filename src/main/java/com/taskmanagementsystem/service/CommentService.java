package com.taskmanagementsystem.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanagementsystem.dto.CommentProjection;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.exception.CommentOperationException;
import com.taskmanagementsystem.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Transactional
	public Map<String, String> createNewComment(Comment comment) {

		if (comment == null || comment.getText() == null || comment.getText().trim().isEmpty()) {
			throw new CommentOperationException("ADDFAILS", "Comment text cannot be empty");
		}
		if (comment.getUser() == null || comment.getTask() == null) {
			throw new CommentOperationException("ADDFAILS", "Comment must be associated with a valid User and Task");
		}

		commentRepository.save(comment);
		Map<String, String> response = new HashMap<>();
		response.put("code", "POSTSUCCESS");
		response.put("message", "Comment added successfully");

		return response;
	}

	@Transactional
	public List<CommentProjection> getListOfComments() {
		List<CommentProjection> listOfComments = commentRepository.findAllComments();
		if (listOfComments.isEmpty()) {
			throw new CommentOperationException("GETFAILS", "Comment list is empty");
		}

		return listOfComments;
	}

	@Transactional
	public CommentProjection getCommentByCommentId(int commentId) {

		Optional<CommentProjection> commentProjection = commentRepository.findCommentById(commentId);

		return commentProjection.orElseThrow(() -> new CommentOperationException("GETFAILS", "Comment does not exist"));
	}
	
	@Transactional
	public String updateCommentDetails(int commentId, Comment comment) {
	    
	    Optional<Comment> existingComment = commentRepository.findById(commentId);
	    
	    if (!existingComment.isPresent()) {
	        throw new CommentOperationException("UPDATEFAIL", "Comment does not exist");
	    }

	  
	    Comment updatedComment = existingComment.get();
	    updatedComment.setText(comment.getText());
	    updatedComment.setCreatedAt(comment.getCreatedAt());
	    updatedComment.setUser(comment.getUser());
	    updatedComment.setTask(comment.getTask());

	    
	    commentRepository.save(updatedComment);
		return "Comment updated successfully";

	}

	public Map<String,String> deleteComment(int commentId) {
		
		Optional<Comment> existingComment = commentRepository.findById(commentId);
	    
	    if (!existingComment.isPresent()) {
	        throw new CommentOperationException("DELETEFAIL", "Comment does not exist");
	    }
	    
	    commentRepository.deleteById(commentId);
	    Map<String, String> response = new LinkedHashMap<>();
		response.put("code", "DELETESUCCESS");
		response.put("Message", "Comment deleted successfully");

		return response;
		
	}	

}
