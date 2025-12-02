package main.web.services.fitsense.metrics.interfaces.rest.resources;

public record UpdateHydrationResource(
        Double quantity,
        Double hydrationGoal,
        Long athleteId
) {
}
