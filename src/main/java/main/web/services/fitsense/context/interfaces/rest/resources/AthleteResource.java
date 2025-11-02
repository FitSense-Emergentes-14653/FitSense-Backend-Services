package main.web.services.fitsense.context.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.List;

/**
 * Athlete Resource
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record AthleteResource(
        Long id,
        String fullname,
        String phone,
        String gender,
        Integer age,
        Double weight,
        Double height,
        String goal,
        String activityLevel,
        List<String> equipment,
        String environment,
        Integer frecuency,
        Long userId
) {
}
