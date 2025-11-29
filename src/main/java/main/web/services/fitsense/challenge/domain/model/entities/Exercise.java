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

    private String level;
    private String equipment;
    private String primaryMuscle;
    private String secondaryMuscle;
    private String category;
    private String imageUrl;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String raw;
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
