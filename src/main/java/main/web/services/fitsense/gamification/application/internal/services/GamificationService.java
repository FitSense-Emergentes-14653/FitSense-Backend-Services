package main.web.services.fitsense.gamification.application.internal.services;

import main.web.services.fitsense.challenge.repositories.CompletedExerciseRepository;
import main.web.services.fitsense.gamification.domain.model.entities.Achievement;
import main.web.services.fitsense.gamification.domain.model.entities.UserAchievement;
import main.web.services.fitsense.gamification.infrastructure.persistence.jpa.repositories.AchievementRepository;
import main.web.services.fitsense.gamification.infrastructure.persistence.jpa.repositories.UserAchievementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GamificationService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final CompletedExerciseRepository completedExerciseRepository;

    public GamificationService(AchievementRepository achievementRepository,
                               UserAchievementRepository userAchievementRepository,
                               CompletedExerciseRepository completedExerciseRepository) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.completedExerciseRepository = completedExerciseRepository;
    }

    public List<Achievement> checkAndUnlockAchievements(String userId) {
        List<Achievement> unlocked = new ArrayList<>();

        long completedExercises = completedExerciseRepository.countByUserId(userId);
        double totalCalories = completedExerciseRepository.sumCaloriesByUserId(userId);
        long streakDays = 3;

        for (Achievement a : achievementRepository.findAll()) {
            if (userAchievementRepository.existsByUserIdAndAchievement_Id(userId, a.getId()))
                continue;

            boolean achieved = switch (a.getCriteriaType()) {
                case "EXERCISES_COMPLETED" -> completedExercises >= a.getCriteriaValue();
                case "CALORIES_BURNED" -> totalCalories >= a.getCriteriaValue();
                case "DAYS_STREAK" -> streakDays >= a.getCriteriaValue();
                default -> false;
            };

            if (achieved) {
                userAchievementRepository.save(new UserAchievement(userId, a));
                unlocked.add(a);
            }
        }

        return unlocked;
    }

    public List<UserAchievement> getUserAchievements(String userId) {
        return userAchievementRepository.findByUserId(userId);
    }
}
