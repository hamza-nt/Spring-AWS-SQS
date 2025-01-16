package com.vde.notifications_app.controller;

import com.vde.notifications_app.model.UserNotification;
import com.vde.notifications_app.service.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationSender notificationSender;

    @PostMapping("/send")
    public String sendNotification(@RequestBody UserNotification notification) {
        notificationSender.sendNotification(notification);
        return "Notification sent successfully!";
    }
}