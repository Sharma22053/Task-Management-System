package com.taskmanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.taskmanagementsystem.dto.CommentProjection;
import com.taskmanagementsystem.dto.NotificationProjection;
import com.taskmanagementsystem.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

	@Query("SELECT c.commentId AS commentId, c.text AS text, c.createdAt AS createdAt, " +
	           "c.user.userId AS userId, c.task.taskId AS taskId " +
	           "FROM Comment c")
	    List<CommentProjection> findAllComments();
	
	@Query("SELECT c.commentId AS commentId, c.text AS text, c.createdAt AS createdAt, " +
	           "c.user.userId AS userId, c.task.taskId AS taskId " +
	           "FROM Comment c WHERE c.commentId = :commentId")
	    Optional<CommentProjection> findCommentById(int commentId);
	
	@Query("SELECT c.commentId AS commentId, c.text AS text, c.createdAt AS createdAt, c.user.userId AS userId, c.task.taskId AS taskId " +
            "FROM Comment c WHERE c.commentId = :commentId")
    Optional<CommentProjection> findCommentProjectionById(int commentId);
	
	@Query("SELECT c.commentId AS commentId, c.text AS text, c.createdAt AS createdAt, c.user.userId AS userId, c.task.taskId AS taskId " +
            "FROM Comment c WHERE c.user.userId = :userId")
	List<CommentProjection> findCommentsByUserId(int userId);
	
}
