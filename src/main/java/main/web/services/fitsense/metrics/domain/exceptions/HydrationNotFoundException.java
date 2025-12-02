package main.web.services.fitsense.metrics.domain.exceptions;

/**
 * * Exception thrown when a athlete is not found in the system.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class HydrationNotFoundException extends RuntimeException {
    public HydrationNotFoundException(Long HydrationId) {
        super("Hydration with id " + HydrationId + " not found");
    }
}
