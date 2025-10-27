package main.web.services.fitsense.context.domain.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Fullname(String fullname) {
    public Fullname {
        if (fullname == null || fullname.isBlank()) {
            throw new IllegalArgumentException("Fullname cannot be null or empty");
        }
        if (fullname.length() < 3 || fullname.length() > 100) {
            throw new IllegalArgumentException("Fullname must be between 3 and 100 characters");
        }
    }
}
