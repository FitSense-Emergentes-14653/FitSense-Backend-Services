package main.web.services.fitsense.metrics.domain.services;

import main.web.services.fitsense.metrics.domain.model.commands.CreateHydrationCommand;
import main.web.services.fitsense.metrics.domain.model.commands.UpdateHydrationCommand;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public interface HydrationCommandService {
    Long handle(CreateHydrationCommand createHydrationCommand);
    Long handle(UpdateHydrationCommand updateHydrationCommand);
}
