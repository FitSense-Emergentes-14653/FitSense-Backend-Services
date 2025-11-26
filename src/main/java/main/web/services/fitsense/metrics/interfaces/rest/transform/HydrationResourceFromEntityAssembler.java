package main.web.services.fitsense.metrics.interfaces.rest.transform;

import main.web.services.fitsense.metrics.domain.model.aggregates.Hydration;
import main.web.services.fitsense.metrics.interfaces.rest.resources.HydrationResource;

/**
 * Assembler class to transform Hydration entity to HydrationResource.
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class HydrationResourceFromEntityAssembler {
    public static HydrationResource toResourceFromEntity(Hydration entity) {
        return new HydrationResource(
                entity.getId(),
                entity.getQuantity().quantity(),
                entity.progressPercentage(),
                entity.getHydrationGoal(),
                entity.getAthlete().getId()
        );
    }
}

