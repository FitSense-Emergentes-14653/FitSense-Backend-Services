package main.web.services.fitsense.context.domain.model.commands;

import java.util.List;

/**
 * UpdateAthleteCommand record represents the command to update an existing Athlete with new details.
 *
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record UpdateAthleteCommand(
        Long athleteId,
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
