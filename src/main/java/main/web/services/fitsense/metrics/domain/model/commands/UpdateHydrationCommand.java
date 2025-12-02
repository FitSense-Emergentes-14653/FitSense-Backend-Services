package main.web.services.fitsense.metrics.domain.model.commands;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record UpdateHydrationCommand(
        Long hydrationId,
        Double quantity,
        Double hydrationGoal,
        Long athleteId
) {
}

