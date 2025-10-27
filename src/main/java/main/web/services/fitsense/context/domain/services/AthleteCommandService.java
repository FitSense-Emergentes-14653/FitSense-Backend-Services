package main.web.services.fitsense.context.domain.services;

import main.web.services.fitsense.context.domain.model.aggregates.Athlete;
import main.web.services.fitsense.context.domain.model.commands.CreateAthleteCommand;
import main.web.services.fitsense.context.domain.model.commands.DeleteAthleteCommand;
import main.web.services.fitsense.context.domain.model.commands.UpdateAthleteCommand;

import java.util.Optional;

/**
 * Service interface for handling Athlete-related commands.
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public interface AthleteCommandService {
    Long handle(CreateAthleteCommand command);
    Long handle(UpdateAthleteCommand command);
    Optional<Athlete> handle(DeleteAthleteCommand command);
}
