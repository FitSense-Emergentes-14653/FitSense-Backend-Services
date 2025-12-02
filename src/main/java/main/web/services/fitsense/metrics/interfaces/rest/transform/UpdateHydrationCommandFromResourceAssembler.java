package main.web.services.fitsense.metrics.interfaces.rest.transform;

import main.web.services.fitsense.metrics.domain.model.commands.UpdateHydrationCommand;
import main.web.services.fitsense.metrics.interfaces.rest.resources.UpdateHydrationResource;

/**
 * Assembler para transformar recursos REST en comandos de actualización de Athlete.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class UpdateHydrationCommandFromResourceAssembler {
    public static UpdateHydrationCommand toCommandFromResource(UpdateHydrationResource resource, Long HydrationId) {
        return new UpdateHydrationCommand(
                HydrationId,
                resource.quantity(),
                resource.hydrationGoal(),
                resource.athleteId()
        );
    }
}
