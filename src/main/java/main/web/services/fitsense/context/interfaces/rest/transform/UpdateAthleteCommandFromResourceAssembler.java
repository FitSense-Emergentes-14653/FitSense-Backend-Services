package main.web.services.fitsense.context.interfaces.rest.transform;

import main.web.services.fitsense.context.domain.model.commands.UpdateAthleteCommand;
import main.web.services.fitsense.context.interfaces.rest.resources.UpdateAthleteResource;

/**
 * Assembler para transformar recursos REST en comandos de actualización de Athlete.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class UpdateAthleteCommandFromResourceAssembler {
    public static UpdateAthleteCommand toCommandFromResource(UpdateAthleteResource resource, Long AthleteId) {
        return new UpdateAthleteCommand(
                AthleteId,
                resource.fullname(),
                resource.phone(),
                resource.gender(),
                resource.age(),
                resource.weight(),
                resource.height(),
                resource.goal(),
                resource.activityLevel(),
                resource.equipment(),
                resource.environment(),
                resource.frecuency(),
                resource.userId()
        );
    }
}
