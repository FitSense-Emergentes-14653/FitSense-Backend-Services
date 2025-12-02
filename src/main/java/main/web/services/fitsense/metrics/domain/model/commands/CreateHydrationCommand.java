package main.web.services.fitsense.metrics.domain.model.commands;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record CreateHydrationCommand(
        Double quantity,
        Double hydrationGoal,
        Long athleteId
) {
}
