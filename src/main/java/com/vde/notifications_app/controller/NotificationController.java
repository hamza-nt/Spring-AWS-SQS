package com.vde.notifications_app.controller;

import com.vde.notifications_app.model.UserNotification;
import com.vde.notifications_app.service.NotificationListener;
import com.vde.notifications_app.service.NotificationSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationSender notificationSender;

    @Autowired
    private NotificationListener notificationListener;

    @PostMapping
    public String sendNotification(@RequestBody UserNotification notification) {
        notificationSender.sendNotification(notification);
        return "Notification envoyée avec succès !";
    }

    @GetMapping("/fetch")
    public String testReceive() {
        notificationListener.receiveNotification();
        return "Notifications reçues et stockées";
    }

    @GetMapping
    public List<UserNotification> getReceivedNotifications() {
        return notificationListener.getReceivedNotifications();
    }

}