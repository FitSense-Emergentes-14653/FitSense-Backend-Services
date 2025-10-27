package main.web.services.fitsense.context.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

/**
 * UpdateAthleteResource record representing the data required to update an Athlete.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record UpdateAthleteResource(
        String fullname,
        String phone,
        String gender,
        Integer age,
        Double weight,
        Double height,
        String goal,
        String activityLevel,
        List<String> equipment,
        Long userId
) {
}
