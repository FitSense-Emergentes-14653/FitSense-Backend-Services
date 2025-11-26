package main.web.services.fitsense.metrics.interfaces.rest.transform;

import main.web.services.fitsense.metrics.domain.model.commands.CreateHydrationCommand;
import main.web.services.fitsense.metrics.interfaces.rest.resources.CreateHydrationResource;

/**
 * Creates a CreateAthleteCommand from a CreateAthleteResource.
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class CreateHydrationCommandFromEntityAssembler {
    public static CreateHydrationCommand toCommandFromResource(CreateHydrationResource resource){
        return new CreateHydrationCommand(
                resource.quantity(),
                resource.hydrationGoal(),
                resource.athleteId()
        );
    }
}
