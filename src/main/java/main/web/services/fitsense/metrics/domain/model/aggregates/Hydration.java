package main.web.services.fitsense.metrics.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.web.services.fitsense.context.domain.model.aggregates.Athlete;
import main.web.services.fitsense.metrics.domain.model.commands.CreateHydrationCommand;
import main.web.services.fitsense.metrics.domain.valueobjects.Quantity;
import main.web.services.fitsense.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
@Getter
@Entity
@Setter
public class Hydration extends AuditableAbstractAggregateRoot<Hydration> {

    @Setter
    @Embedded
    private Quantity quantity;

    @Column(nullable = false)
    private Double hydrationGoal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

    public Hydration(CreateHydrationCommand command, Athlete athlete) {
        this.quantity = new Quantity(command.quantity());
        this.hydrationGoal = command.hydrationGoal() != null ? command.hydrationGoal() : 2000.0;
        this.athlete = athlete;
    }

    public Hydration() {
    }

    public void UpdateHydration(Double quantity) {
        this.quantity = new Quantity(quantity);
    }

    public void addWater(Double amount) {
        this.quantity = new Quantity(this.quantity.quantity() + amount);
    }

    public Double progressPercentage() {
        return (this.quantity.quantity() / this.hydrationGoal) * 100;
    }

    public boolean goalReached() {
        return this.quantity.quantity() >= this.hydrationGoal;
    }


}
