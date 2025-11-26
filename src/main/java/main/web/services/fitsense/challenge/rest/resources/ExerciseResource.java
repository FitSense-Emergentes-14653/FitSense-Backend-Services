package main.web.services.fitsense.challenge.rest.resources;

/**
 * Represents an exercise from the master "exercises" table.
 * Used to display exercise metadata in routines or standalone queries.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.1
 */
public record ExerciseResource(
        Long id,
        String name,
        String level,
        String equipment,
        String primaryMuscle,
        String secondaryMuscle,
        String category,
        String imageUrl,
        String raw
) {}
