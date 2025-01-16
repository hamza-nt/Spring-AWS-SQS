package com.vde.notifications_app.model;

import lombok.Data;

@Data
public class UserNotification {
    private String userId;
    private String type;
    private String message;
}