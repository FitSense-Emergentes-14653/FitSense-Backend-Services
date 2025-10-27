package main.web.services.fitsense.context.domain.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record ActivityLevel(String activityLevel) {
    public ActivityLevel {
        if (activityLevel == null || activityLevel.isBlank()) {
            throw new IllegalArgumentException("Activity level cannot be null or empty");
        }
        String normalized = activityLevel.trim().toLowerCase();
        if (!normalized.equals("principiante") &&
                !normalized.equals("intermedio") &&
                !normalized.equals("avanzado")) {
            throw new IllegalArgumentException("Activity level must be 'Principiante', 'Intermedio' or 'Avanzado'");
        }
    }
}
