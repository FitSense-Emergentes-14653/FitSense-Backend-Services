package main.web.services.fitsense.context.domain.model.commands;

import java.time.LocalDate;
import java.util.List;

/**
 * CreateAthleteCommand record represents the command to create a new Athlete with the necessary details.
 *
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record CreateAthleteCommand(
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
