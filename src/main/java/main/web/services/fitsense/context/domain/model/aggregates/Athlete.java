package main.web.services.fitsense.context.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.web.services.fitsense.context.domain.model.commands.CreateAthleteCommand;
import main.web.services.fitsense.context.domain.valueobjects.*;
import main.web.services.fitsense.iam.domain.model.aggregates.User;
import main.web.services.fitsense.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

/**
 * Athlete aggregate root.
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
@Getter
@Entity
@Setter
public class Athlete extends AuditableAbstractAggregateRoot<Athlete> {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Embedded
    private Fullname fullname;

    @Setter
    @Embedded
    private Phone phone;

    @Setter
    @Embedded
    private Gender gender;

    @Setter
    @Embedded
    private Age age;

    @Setter
    @Embedded
    private Weight weight;

    @Setter
    @Embedded
    private Height height;

    @Setter
    @Embedded
    private Goal goal;

    @Setter
    @Embedded
    private ActivityLevel activityLevel;

    @Setter
    @Embedded
    private Equipment equipment;

    @Setter
    @Embedded
    private Environment environment;

    @Setter
    @Embedded
    private Frecuency frecuency;


    public Athlete(CreateAthleteCommand command, User user) {
        this.fullname = new Fullname(command.fullname());
        this.phone = new Phone(command.phone());
        this.user = user;
        this.gender = new Gender(command.gender());
        this.age = new Age(command.age());
        this.weight = new Weight(command.weight());
        this.height = new Height(command.height());
        this.goal = new Goal(command.goal());
        this.activityLevel = new ActivityLevel(command.activityLevel());
        this.equipment = new Equipment(command.equipment());
        this.environment = new Environment(command.environment());
        this.frecuency = new Frecuency(command.frecuency());
    }

    public Athlete() {}

    public void updateAthlete(String fullname, String phone,
                              String gender, Integer age,
                              Double weight,
                              Double height, String goal,
                              String activityLevel, List<String> equipment, String environment, Integer frecuency) {

        this.fullname = new Fullname(fullname);
        this.phone = new Phone(phone);
        this.gender = new Gender(gender);
        this.age = new Age(age);
        this.weight = new Weight(weight);
        this.height = new Height(height);
        this.goal = new Goal(goal);
        this.activityLevel = new ActivityLevel(activityLevel);
        this.equipment = new Equipment(equipment);
        this.environment = new Environment(environment);
        this.frecuency = new Frecuency(frecuency);
    }

    public Long getUserId() {
        return this.user.getId();
    }
}
