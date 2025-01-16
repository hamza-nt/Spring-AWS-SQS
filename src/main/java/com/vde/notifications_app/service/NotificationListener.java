package com.vde.notifications_app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import com.vde.notifications_app.model.UserNotification;

@Service
public class NotificationListener {

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private ObjectMapper objectMapper;

    public void receiveNotification() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl("http://localhost:4566/000000000000/notification-queue")
                .maxNumberOfMessages(10)
                .build();

        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(receiveMessageRequest);

        for (Message message : receiveMessageResponse.messages()) {
            try {
                UserNotification notification = objectMapper.readValue(message.body(), UserNotification.class);
                System.out.println("Received notification: " + notification);
                // Traitez la notification ici (par exemple, envoyez-la par email ou SMS)
            } catch (Exception e) {
                System.err.println("Failed to deserialize message: " + e.getMessage());
            }
        }
    }
}