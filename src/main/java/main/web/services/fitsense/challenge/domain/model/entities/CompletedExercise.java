package main.web.services.fitsense.challenge.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "completed_exercises")
public class CompletedExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String exerciseName;

    @Column(nullable = false)
    private Double caloriesBurned;

    @Column(nullable = false)
    private Long routineId;

    @Column(nullable = false)
    private LocalDateTime completedAt;
}
