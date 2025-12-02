package main.web.services.fitsense.shared.infrastructure.persistence.jpa.repositories;

import main.web.services.fitsense.shared.domain.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByOrderByCreatedAtDesc();
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
}
