package main.web.services.fitsense.context.domain.model.commands;

import java.time.LocalDate;

/**
 * UpdateAthleteCommand record represents the command to update an existing Athlete with the necessary details.
 *
 * @author Fiorella Jarama Peñaloza
 */
public record UpdateAthleteCommand (Long athleteId,
                                       String fullname,
                                       String phone,
                                       Long userId) {
}
