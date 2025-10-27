package main.web.services.fitsense.context.domain.exceptions;

/**
 * * Exception thrown when there is an error saving a athlete.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class AthleteSaveException extends RuntimeException {
    public AthleteSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
