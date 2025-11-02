package main.web.services.fitsense.context.domain.valueobjects;

/**
 * Environment value object representing the workout environment
 * where the athlete usually trains (e.g., home, gym, outdoor).
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Environment(String environment) {
    public Environment {
        if (environment == null || environment.isBlank()) {
            throw new IllegalArgumentException("Environment cannot be null or empty");
        }
    }
}
