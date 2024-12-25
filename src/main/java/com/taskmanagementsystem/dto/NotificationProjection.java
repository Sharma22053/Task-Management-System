package com.taskmanagementsystem.dto;


import java.util.Date;

public interface NotificationProjection {
    Integer getNotificationId();
    String getText();
    Date getCreatedAt();
    Integer getUserId();
}