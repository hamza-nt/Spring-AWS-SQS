package com.vde.notifications_app;

import com.vde.notifications_app.model.UserNotification;
import com.vde.notifications_app.service.NotificationSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class NotificationsAppApplicationTests extends AbstractIntegrationTest {

	@Autowired
	private NotificationSender notificationSender;

	@Test
	public void testSendAndReceiveNotification() {
		UserNotification notification = new UserNotification();
		notification.setUserId("123");
		notification.setType("PASSWORD_RESET");
		notification.setMessage("Your password has been reset successfully.");

		notificationSender.sendNotification(notification);
	}
}