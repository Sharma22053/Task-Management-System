package com.taskmanagementsystem.dto;

import java.util.Date;

public interface CommentProjection {
    int getCommentId();
    String getText();
    Date getCreatedAt();
    Integer getUserId(); 
    Integer getTaskId(); 
}