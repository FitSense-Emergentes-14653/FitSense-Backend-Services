package main.web.services.fitsense.context.domain.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Age(Integer age) {
    public Age {
        if (age == null) {
            throw new IllegalArgumentException("Age cannot be null");
        }
        if (age < 10 || age > 100) {
            throw new IllegalArgumentException("Age must be between 10 and 100 years");
        }
    }
}
