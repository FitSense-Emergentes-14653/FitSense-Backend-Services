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
        if (height < 100 || height > 250) {
            throw new IllegalArgumentException("Height must be between 100 and 250 cm");
        }
    }
}
