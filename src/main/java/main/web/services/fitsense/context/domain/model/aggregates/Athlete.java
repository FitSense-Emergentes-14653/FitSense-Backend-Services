package main.web.services.fitsense.context.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.web.services.fitsense.context.domain.model.commands.CreateAthleteCommand;
import main.web.services.fitsense.context.domain.valueobjects.Fullname;
import main.web.services.fitsense.context.domain.valueobjects.Phone;
import main.web.services.fitsense.iam.domain.model.aggregates.User;
import main.web.services.fitsense.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Athlete aggregate root.
 *
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


    public Athlete(CreateAthleteCommand command, User user) {
        this.fullname = new Fullname(command.fullname());
        this.phone = new Phone(command.phone());
        this.user = user;

    }

    public Athlete() {}

    public void updateAthlete(String fullname, String phone) {
        this.fullname = new Fullname(fullname);
        this.phone = new Phone(phone);
    }

    public Long getUserId() {
        return this.user.getId();
    }
}
