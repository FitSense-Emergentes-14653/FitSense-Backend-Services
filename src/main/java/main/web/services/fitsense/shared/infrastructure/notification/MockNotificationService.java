package main.web.services.fitsense.shared.infrastructure.notification;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("local")
@Primary
@Service
public class MockNotificationService extends NotificationService {

    @Override
    public String sendPushNotification(String deviceToken, String title, String body) {
        return "mock-message-id-" + System.currentTimeMillis();
    }
}