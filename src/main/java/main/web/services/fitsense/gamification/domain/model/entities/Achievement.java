package main.web.services.fitsense.gamification.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.web.services.fitsense.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Achievement entity represents a gamification goal that users can unlock.
 *
 * @author Fiorella
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "achievements")
public class Achievement extends AuditableAbstractAggregateRoot<Achievement> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String criteriaType;

    @Column(nullable = false)
    private Integer criteriaValue;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer rewardPoints;

    public Achievement(String code, String criteriaType, Integer criteriaValue, String description,
                       String imageUrl, String name, Integer rewardPoints) {
        this.code = code;
        this.criteriaType = criteriaType;
        this.criteriaValue = criteriaValue;
        this.description = description;
        this.imageUrl = imageUrl;
        this.name = name;
        this.rewardPoints = rewardPoints;
    }
}
