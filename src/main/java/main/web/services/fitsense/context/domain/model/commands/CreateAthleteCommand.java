package main.web.services.fitsense.context.domain.model.commands;

import java.time.LocalDate;

/**
 * CreateAthleteCommand record represents the command to create a new Athlete with the necessary details.
 *
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record CreateAthleteCommand
        (String fullname,
         String phone,
         Long userId
        ) {
}
