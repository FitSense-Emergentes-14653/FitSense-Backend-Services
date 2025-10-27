package main.web.services.fitsense.context.domain.exceptions;

/**
 * * Exception thrown when a athlete is not found in the system.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class AthleteNotFoundException extends RuntimeException {
    public AthleteNotFoundException(Long AthleteId) {
        super("Athlete with id " + AthleteId + " not found");
    }
}
