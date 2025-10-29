package main.web.services.fitsense.context.domain.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Goal(String goal) {
    public Goal {
        if (goal == null || goal.isBlank()) {
            throw new IllegalArgumentException("Goal cannot be null or empty");
        }
        String normalized = goal.trim().toLowerCase();
        // Accept both Spanish and English values
        if (!normalized.equals("perder peso") &&
                !normalized.equals("ganar peso") &&
                !normalized.equals("aumento de masa muscular") &&
                !normalized.equals("moldear el cuerpo") &&
                !normalized.equals("otros") &&
                !normalized.equals("weight_loss") &&
                !normalized.equals("lose_weight") &&
                !normalized.equals("weight_gain") &&
                !normalized.equals("gain_weight") &&
                !normalized.equals("muscle_gain") &&
                !normalized.equals("strength") &&
                !normalized.equals("body_shaping") &&
                !normalized.equals("toning") &&
                !normalized.equals("other")) {
            throw new IllegalArgumentException("Invalid goal option: " + normalized);
        }
    }
}
