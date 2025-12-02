package main.web.services.fitsense.gamification.infrastructure.persistence.jpa.repositories;

import main.web.services.fitsense.gamification.domain.model.entities.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    boolean existsByUserIdAndAchievement_Id(String userId, Long achievementId);
    List<UserAchievement> findByUserId(String userId);
}
