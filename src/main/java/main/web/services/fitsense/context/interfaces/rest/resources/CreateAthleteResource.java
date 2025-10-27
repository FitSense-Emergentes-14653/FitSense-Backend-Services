package main.web.services.fitsense.context.interfaces.rest.resources;

import java.time.LocalDate;

/**
 * Create Athlete Resource
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record CreateAthleteResource(String fullname, String phone, Long userId) {
}
