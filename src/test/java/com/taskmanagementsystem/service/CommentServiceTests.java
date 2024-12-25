package com.taskmanagementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taskmanagementsystem.dto.CommentProjection;
import com.taskmanagementsystem.entity.Comment;
import com.taskmanagementsystem.exception.CommentOperationException;
import com.taskmanagementsystem.repository.CommentRepository;

class CommentServiceTests {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewComment_shouldThrowException_whenCommentTextIsEmpty() {
        Comment comment = new Comment();
        comment.setText("");

        CommentOperationException exception = assertThrows(CommentOperationException.class, () -> 
            commentService.createNewComment(comment)
        );

        assertEquals("ADDFAILS", exception.getCode());
        assertEquals("Comment text cannot be empty", exception.getMessage());
    }


    @Test
    void getListOfComments_shouldThrowException_whenNoCommentsExist() {
        when(commentRepository.findAllComments()).thenReturn(Collections.emptyList());

        CommentOperationException exception = assertThrows(CommentOperationException.class, () -> 
            commentService.getListOfComments()
        );

        assertEquals("GETFAILS", exception.getCode());
        assertEquals("Comment list is empty", exception.getMessage());
    }

    @Test
    void getListOfComments_shouldReturnCommentList_whenCommentsExist() {
        CommentProjection mockProjection = mock(CommentProjection.class);
        when(commentRepository.findAllComments()).thenReturn(List.of(mockProjection));

        List<CommentProjection> comments = commentService.getListOfComments();

        assertEquals(1, comments.size());
        assertSame(mockProjection, comments.get(0));
    }

    @Test
    void getCommentByCommentId_shouldThrowException_whenCommentNotFound() {
        when(commentRepository.findCommentById(1)).thenReturn(Optional.empty());

        CommentOperationException exception = assertThrows(CommentOperationException.class, () -> 
            commentService.getCommentByCommentId(1)
        );

        assertEquals("GETFAILS", exception.getCode());
        assertEquals("Comment does not exist", exception.getMessage());
    }

    @Test
    void getCommentByCommentId_shouldReturnCommentProjection_whenCommentExists() {
        CommentProjection mockProjection = mock(CommentProjection.class);
        when(commentRepository.findCommentById(1)).thenReturn(Optional.of(mockProjection));

        CommentProjection comment = commentService.getCommentByCommentId(1);

        assertSame(mockProjection, comment);
    }

    @Test
    void updateCommentDetails_shouldThrowException_whenCommentNotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        Comment comment = new Comment();
        CommentOperationException exception = assertThrows(CommentOperationException.class, () -> 
            commentService.updateCommentDetails(1, comment)
        );

        assertEquals("UPDATEFAIL", exception.getCode());
        assertEquals("Comment does not exist", exception.getMessage());
    }

    @Test
    void updateCommentDetails_shouldReturnSuccessMessage_whenCommentUpdated() {
        Comment existingComment = new Comment();
        Comment updatedComment = new Comment();
        updatedComment.setText("Updated text");

        when(commentRepository.findById(1)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(existingComment)).thenReturn(existingComment);

        String response = commentService.updateCommentDetails(1, updatedComment);

        assertEquals("Comment updated successfully", response);
    }

    @Test
    void deleteComment_shouldThrowException_whenCommentNotFound() {
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        CommentOperationException exception = assertThrows(CommentOperationException.class, () -> 
            commentService.deleteComment(1)
        );

        assertEquals("DELETEFAIL", exception.getCode());
        assertEquals("Comment does not exist", exception.getMessage());
    }

    @Test
    void deleteComment_shouldReturnSuccessResponse_whenCommentDeleted() {
        Comment comment = new Comment();
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        doNothing().when(commentRepository).deleteById(1);

        Map<String, String> response = commentService.deleteComment(1);

        assertEquals("DELETESUCCESS", response.get("code"));
        assertEquals("Comment deleted successfully", response.get("Message"));
    }
}
