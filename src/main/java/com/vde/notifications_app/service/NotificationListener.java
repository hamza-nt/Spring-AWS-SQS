package com.vde.notifications_app.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationListener {

    private final AmazonSQSAsync sqsClient;

    @Value("${sqs.queue.name}")
    private String queueName;

    public NotificationListener(AmazonSQSAsync sqsClient) {
        this.sqsClient = sqsClient;
    }

    @MessageMapping
    public void listenNotifications() {
        String queueUrl = sqsClient.getQueueUrl(queueName).getQueueUrl();
        List<Message> messages = sqsClient.receiveMessage(queueUrl).getMessages();

        for (Message message : messages) {
            System.out.println("Received notification: " + message.getBody());
            sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
        }
    }
}
