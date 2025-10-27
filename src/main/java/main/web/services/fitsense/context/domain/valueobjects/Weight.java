package main.web.services.fitsense.context.domain.valueobjects;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Weight(Double weight) {
    public Weight {
        if (weight == null || weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
    }
}
