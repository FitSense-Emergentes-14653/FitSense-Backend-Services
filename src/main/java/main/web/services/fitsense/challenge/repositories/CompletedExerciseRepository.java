package main.web.services.fitsense.challenge.repositories;

import main.web.services.fitsense.challenge.domain.model.entities.CompletedExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Descripción de la clase.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public interface CompletedExerciseRepository extends JpaRepository<CompletedExercise, Long> {
    List<CompletedExercise> findByUserIdAndRoutineId(String userId, Long routineId);

    @Query("SELECT COUNT(c) FROM CompletedExercise c WHERE c.userId = :userId")
    long countByUserId(String userId);

    @Query("SELECT COALESCE(SUM(c.caloriesBurned), 0) FROM CompletedExercise c WHERE c.userId = :userId")
    double sumCaloriesByUserId(String userId);

}
