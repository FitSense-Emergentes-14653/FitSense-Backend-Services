package main.web.services.fitsense.metrics.interfaces.rest.resources;

public record HydrationResource(
        Long id,
        Double quantity,
        Double progressPercentage,
        Double dailyGoal,
        Long athleteId
) { }
