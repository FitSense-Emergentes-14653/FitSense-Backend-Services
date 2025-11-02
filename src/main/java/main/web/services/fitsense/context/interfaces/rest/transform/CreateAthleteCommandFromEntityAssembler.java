package main.web.services.fitsense.context.interfaces.rest.transform;

import main.web.services.fitsense.context.domain.model.commands.CreateAthleteCommand;
import main.web.services.fitsense.context.interfaces.rest.resources.CreateAthleteResource;

/**
 * Creates a CreateAthleteCommand from a CreateAthleteResource.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class CreateAthleteCommandFromEntityAssembler {
    public static CreateAthleteCommand toCommandFromResource(CreateAthleteResource resource){
        return new CreateAthleteCommand(
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
