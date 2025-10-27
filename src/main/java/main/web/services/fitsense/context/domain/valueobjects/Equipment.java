package main.web.services.fitsense.context.domain.valueobjects;

import java.util.List;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record Equipment(List<String> equipment) {
    public Equipment {
        if (equipment == null || equipment.isEmpty()) {
            throw new IllegalArgumentException("Equipment list cannot be null or empty");
        }
    }
}
