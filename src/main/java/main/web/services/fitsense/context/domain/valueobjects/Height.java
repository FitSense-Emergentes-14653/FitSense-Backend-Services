package main.web.services.fitsense.context.domain.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Height(Double height) {
    public Height {
        if (height == null || height <= 0) {
            throw new IllegalArgumentException("Height must be greater than zero");
        }
        if (height < 50 || height > 250) {
            throw new IllegalArgumentException("Height must be between 50 and 250 cm");
        }
    }
}
