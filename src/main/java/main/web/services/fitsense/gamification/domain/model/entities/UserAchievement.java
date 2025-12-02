package main.web.services.fitsense.gamification.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.web.services.fitsense.shared.domain.model.entities.AuditableModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_achievements")
public class UserAchievement extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id", nullable = false)
    private Achievement achievement;

    private LocalDateTime earnedAt = LocalDateTime.now();

    public UserAchievement() {}
    public UserAchievement(String userId, Achievement achievement) {
        this.userId = userId;
        this.achievement = achievement;
        this.earnedAt = LocalDateTime.now();
    }
}
