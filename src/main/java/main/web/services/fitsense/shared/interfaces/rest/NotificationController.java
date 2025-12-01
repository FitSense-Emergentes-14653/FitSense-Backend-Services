package main.web.services.fitsense.shared.interfaces.rest;

import main.web.services.fitsense.shared.infrastructure.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendNotification(@RequestBody NotificationRequest request) {
        String messageId = notificationService.sendPushNotification(request.token, request.title, request.body);
        return ResponseEntity.ok(Map.of("messageId", messageId));
    }

    public static class NotificationRequest {
        public String token;
        public String title;
        public String body;
    }
}