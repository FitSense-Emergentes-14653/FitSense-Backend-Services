package main.web.services.fitsense.context.domain.valueobjects;

/**
 * Frecuency value object representing the weekly training frequency.
 * Defines how many times per week the athlete trains.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Frecuency(Integer frecuency) {
    public Frecuency {
        if (frecuency == null) {
            throw new IllegalArgumentException("Frecuency cannot be null");
        }
    }
}
