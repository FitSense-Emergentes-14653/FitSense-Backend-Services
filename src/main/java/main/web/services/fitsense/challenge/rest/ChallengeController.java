package main.web.services.fitsense.challenge.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import main.web.services.fitsense.challenge.domain.model.entities.Challenge;
import main.web.services.fitsense.challenge.domain.model.entities.CompletedExercise;
import main.web.services.fitsense.challenge.repositories.ChallengeRepository;
import main.web.services.fitsense.challenge.repositories.CompletedExerciseRepository;
import main.web.services.fitsense.challenge.rest.resources.ChallengeResource;
import main.web.services.fitsense.challenge.rest.transform.ChallengeResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing user-generated challenges (AI-generated routines).
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.1
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/challenges", produces = "application/json")
@Tag(name = "Challenges", description = "Endpoints for AI-generated routines (user challenges)")
public class ChallengeController {

    private final ChallengeRepository challengeRepository;
    private final CompletedExerciseRepository completedExerciseRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public ChallengeController(ChallengeRepository challengeRepository, CompletedExerciseRepository completedExerciseRepository) {
        this.challengeRepository = challengeRepository;
        this.completedExerciseRepository = completedExerciseRepository;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChallengeResource>> getChallengesByUser(@PathVariable String userId) {
        List<Challenge> challenges = challengeRepository.findByUserIdOrderByCreatedAtDesc(userId);

        if (challenges.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resources = challenges.stream()
                .map(ChallengeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }


    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<ChallengeResource> getLatestChallengeByUser(@PathVariable String userId) {
        return challengeRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                .map(ChallengeResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResource> getChallengeById(@PathVariable Long id) {
        return challengeRepository.findById(id)
                .map(ChallengeResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/complete")
    public ResponseEntity<?> markExerciseCompleted(
            @RequestParam String userId,
            @RequestParam Long routineId,
            @RequestParam String exerciseName
    ) {
        // Buscar la rutina
        Optional<Challenge> challengeOpt = challengeRepository.findById(routineId);
        if (challengeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Rutina no encontrada"
            ));
        }

        Challenge challenge = challengeOpt.get();

        try {
            JsonNode root = objectMapper.readTree(challenge.getRutinaJson());
            double calories = 0.0;

            // Buscar el ejercicio dentro del JSON
            for (JsonNode week : root.path("weeks")) {
                for (JsonNode day : week.path("days")) {
                    for (JsonNode exercise : day.path("exercises")) {
                        if (exercise.path("name").asText().equalsIgnoreCase(exerciseName)) {
                            calories = exercise.path("calories_total").asDouble(0.0);
                            break;
                        }
                    }
                }
            }

            // Guardar el progreso
            CompletedExercise completed = new CompletedExercise();
            completed.setUserId(userId);
            completed.setRoutineId(routineId);
            completed.setExerciseName(exerciseName);
            completed.setCaloriesBurned(calories);
            completed.setCompletedAt(LocalDateTime.now());
            completedExerciseRepository.save(completed);

            // 🔹 Respuesta estructurada en JSON
            Map<String, Object> response = Map.of(
                    "status", "success",
                    "message", "Ejercicio completado correctamente",
                    "exercise", Map.of(
                            "name", exerciseName,
                            "caloriesBurned", calories,
                            "completedAt", completed.getCompletedAt()
                    ),
                    "userId", userId,
                    "routineId", routineId
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error procesando rutina",
                    "details", e.getMessage()
            ));
        }
    }


    @GetMapping("/user/{userId}/routine/{routineId}/summary")
    public ResponseEntity<?> getProgressSummary(
            @PathVariable String userId,
            @PathVariable Long routineId
    ) {
        var completedList = completedExerciseRepository.findByUserIdAndRoutineId(userId, routineId);

        double totalCalories = completedList.stream()
                .mapToDouble(CompletedExercise::getCaloriesBurned)
                .sum();

        long exercisesCompleted = completedList.size();

        Map<String, Object> response = Map.of(
                "status", "success",
                "message", "Resumen del progreso recuperado correctamente",
                "data", Map.of(
                        "userId", userId,
                        "routineId", routineId,
                        "exercisesCompleted", exercisesCompleted,
                        "totalCaloriesBurned", totalCalories,
                        "lastUpdated", completedList.stream()
                                .map(CompletedExercise::getCompletedAt)
                                .max(LocalDateTime::compareTo)
                                .orElse(null)
                )
        );

        return ResponseEntity.ok(response);
    }


}
