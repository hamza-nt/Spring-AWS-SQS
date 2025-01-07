package com.vde.notifications_app.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vde.notifications_app.model.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationSenderService {
    private final AmazonSQS sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${sqs.queue.name}")
    private String queueName;

    public NotificationSenderService(AmazonSQS sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    public void sendNotification(Notification notification) throws JsonProcessingException {
        String queueUrl = sqsClient.getQueueUrl(queueName).getQueueUrl();
        String messageBody = objectMapper.writeValueAsString(notification);

        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messageBody);

        sqsClient.sendMessage(request);
    }
}