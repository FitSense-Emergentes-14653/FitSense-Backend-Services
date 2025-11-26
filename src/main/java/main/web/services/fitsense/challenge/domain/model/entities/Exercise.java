package main.web.services.fitsense.challenge.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.web.services.fitsense.shared.domain.model.entities.AuditableModel;

/**
 * Represents an exercise stored in the master "exercises" table.
 * Used by the AI to generate personalized routines for each user.
 *
 * @author Fiorella
 */
@Getter
@Setter
@Entity
@Table(name = "exercises")
public class Exercise extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String level;             // Beginner / Intermediate / Advanced
    private String equipment;         // Dumbbell, Barbell, Bodyweight...
    private String primaryMuscle;     // Ej: Chest, Legs, Core...
    private String secondaryMuscle;   // Ej: Triceps, Shoulders...
    private String category;          // Upper / Lower / Core / FullBody...
    private String imageUrl;          // URL de referencia (GIF o PNG)

    @Lob
    @Column(columnDefinition = "TEXT")
    private String raw;               // JSON o descripción completa del ejercicio

    public Exercise() {}

    public Exercise(String name, String level, String equipment,
                    String primaryMuscle, String secondaryMuscle,
                    String category, String imageUrl, String raw) {
        this.name = name;
        this.level = level;
        this.equipment = equipment;
        this.primaryMuscle = primaryMuscle;
        this.secondaryMuscle = secondaryMuscle;
        this.category = category;
        this.imageUrl = imageUrl;
        this.raw = raw;
    }
}
