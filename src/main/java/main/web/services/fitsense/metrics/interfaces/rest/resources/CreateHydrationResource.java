package main.web.services.fitsense.metrics.interfaces.rest.resources;

public record CreateHydrationResource(
        Double quantity,
        Double hydrationGoal,
        Long athleteId
) {
}
