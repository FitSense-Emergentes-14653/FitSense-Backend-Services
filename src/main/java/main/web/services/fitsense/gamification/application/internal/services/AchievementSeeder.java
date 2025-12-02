package main.web.services.fitsense.gamification.application.internal.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.web.services.fitsense.gamification.domain.model.entities.Achievement;
import main.web.services.fitsense.gamification.infrastructure.persistence.jpa.repositories.AchievementRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds default achievements into the database when the application starts.
 *
 * @author Fiorella
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AchievementSeeder {

    private final AchievementRepository achievementRepository;

    @PostConstruct
    public void seedAchievements() {
        if (achievementRepository.count() > 0) {
            log.info("✅ Achievements already exist, skipping seeding...");
            return;
        }

        log.info("🌱 Seeding default achievements...");

        List<Achievement> achievements = List.of(
                new Achievement(
                        "FIRST_WORKOUT",
                        "EXERCISES_COMPLETED",
                        1,
                        "Completaste tu primer ejercicio. ¡El comienzo de tu nueva rutina!",
                        "https://cdn-icons-png.flaticon.com/512/1150/1150637.png",
                        "Primer entrenamiento",
                        50
                ),
                new Achievement(
                        "FIVE_WORKOUTS",
                        "EXERCISES_COMPLETED",
                        5,
                        "Completaste 5 ejercicios. Vas tomando ritmo.",
                        "https://cdn-icons-png.flaticon.com/512/833/833472.png",
                        "Rutina en marcha",
                        100
                ),
                new Achievement(
                        "HYDRATION_STREAK",
                        "DAYS_STREAK",
                        7,
                        "Cumpliste tu meta de hidratación durante 7 días seguidos.",
                        "https://cdn-icons-png.flaticon.com/512/4150/4150959.png",
                        "Hidratado y constante",
                        150
                ),
                new Achievement(
                        "BURN_500",
                        "CALORIES_BURNED",
                        500,
                        "Has quemado más de 500 calorías entrenando. ¡Sigue así!",
                        "https://cdn-icons-png.flaticon.com/512/3014/3014424.png",
                        "Metabolismo activo",
                        200
                ),
                new Achievement(
                        "WEEK_STREAK",
                        "DAYS_STREAK",
                        7,
                        "Entrenaste durante una semana sin interrupciones. ¡Imparable!",
                        "https://cdn-icons-png.flaticon.com/512/2278/2278992.png",
                        "Constancia semanal",
                        250
                )
        );

        achievementRepository.saveAll(achievements);
        log.info("🏆 {} achievements seeded successfully!", achievements.size());
    }
}