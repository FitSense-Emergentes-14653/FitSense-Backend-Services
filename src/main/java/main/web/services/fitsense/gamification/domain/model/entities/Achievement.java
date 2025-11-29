package main.web.services.fitsense.gamification.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.web.services.fitsense.shared.domain.model.entities.AuditableModel;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String criteriaType;  // EXERCISES_COMPLETED, CALORIES_BURNED, DAYS_STREAK

    @Column(nullable = false)
    private Double criteriaValue;

    private Integer rewardPoints;

    private String imageUrl;
}
