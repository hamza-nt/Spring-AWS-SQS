package com.vde.notifications_app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import com.vde.notifications_app.model.UserNotification;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationListener {

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Getter
    private final List<UserNotification> receivedNotifications = new ArrayList<>();

    public void receiveNotification() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/notification-queue")
                .maxNumberOfMessages(10)
                .build();

        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(receiveMessageRequest);

        for (Message message : receiveMessageResponse.messages()) {
            try {
                UserNotification notification = objectMapper.readValue(message.body(), UserNotification.class);
                System.out.println("Notification reçue : " + notification);
                receivedNotifications.add(notification);
                System.out.println("Nombre total de notifications stockées : " + receivedNotifications.size());
            } catch (Exception e) {
                System.err.println("Échec de la désérialisation du message : " + e.getMessage());
            }
        }
    }
}