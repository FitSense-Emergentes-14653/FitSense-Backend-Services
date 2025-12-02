package main.web.services.fitsense.gamification.infrastructure.persistence.jpa.repositories;

import main.web.services.fitsense.gamification.domain.model.entities.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    Achievement findByCode(String code);
}
