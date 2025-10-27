package main.web.services.fitsense.context.domain.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Gender(String gender) {
    public Gender {
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
        String normalized = gender.trim().toLowerCase();
        if (!normalized.equals("masculino") && !normalized.equals("femenino")) {
            throw new IllegalArgumentException("Gender must be either 'Masculino' or 'Femenino'");
        }
    }
}
