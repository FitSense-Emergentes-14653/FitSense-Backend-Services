package main.web.services.fitsense.context.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * UpdateAthleteResource record representing the data required to update a Athlete.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record UpdateAthleteResource(String fullname, String phone, Long userId) {
}
