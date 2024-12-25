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

import com.taskmanagementsystem.dto.CommentProjection;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.service.CommentService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post")
	public ResponseEntity<Map<String,String>> createNewComment(@RequestBody Comment comment){
		
		return new ResponseEntity<>(commentService.createNewComment(comment),HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CommentProjection>> getListOfComments(){
		
		return new ResponseEntity<>(commentService.getListOfComments(),HttpStatus.OK);
	}
	
	@GetMapping("/{commentId}")
	public ResponseEntity<CommentProjection> getCommentByCommentId(@PathVariable int commentId){
		
		return new ResponseEntity<>(commentService.getCommentByCommentId(commentId),HttpStatus.OK);
	}
	
	@PutMapping("/update/{commentId}")
	public ResponseEntity<String> updateCommentDetails(@PathVariable int commentId,@RequestBody Comment comment){
		
		return new ResponseEntity<>(commentService.updateCommentDetails(commentId,comment),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<Map<String,String>> deleteComment(@PathVariable int commentId){
		
		return new ResponseEntity<>(commentService.deleteComment(commentId),HttpStatus.OK);
	}
}
