package com.vde.notifications_app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import com.vde.notifications_app.model.UserNotification;

@Service
public class NotificationSender {

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendNotification(UserNotification notification) {
        try {
            String messageBody = objectMapper.writeValueAsString(notification);
            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl("http://localhost:4566/000000000000/notification-queue")
                    .messageBody(messageBody)
                    .build();

            sqsClient.sendMessage(sendMessageRequest);
            System.out.println("Notification envoyée : " + notification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Échec de la sérialisation de la notification", e);
        }
    }
}