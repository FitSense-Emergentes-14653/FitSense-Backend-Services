package main.web.services.fitsense.shared.interfaces.rest;

import main.web.services.fitsense.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import main.web.services.fitsense.shared.application.internal.commandservices.NotificationCommandService;
import main.web.services.fitsense.shared.domain.model.entities.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationCommandService notificationCommandService;
    private final UserRepository userRepository;

    public NotificationController(NotificationCommandService notificationCommandService, UserRepository userRepository) {
        this.notificationCommandService = notificationCommandService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        Long currentUserId = getCurrentUserId();
        // Verificar que el usuario solo pueda ver sus propias notificaciones
        if (!currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Notification> notifications = notificationCommandService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationRequest request) {
        Long userId = getCurrentUserId();
        Notification notification = notificationCommandService.createNotification(userId, request.title, request.body);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findUserByEmailIs(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    public record NotificationRequest(String title, String body) {
    }
}