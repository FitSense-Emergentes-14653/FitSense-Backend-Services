package main.web.services.fitsense.context.domain.services;

import main.web.services.fitsense.context.domain.model.aggregates.Athlete;
import main.web.services.fitsense.context.domain.model.queries.GetAllAthletesQuery;
import main.web.services.fitsense.context.domain.model.queries.GetAthleteByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling Athlete-related queries.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public interface AthleteQueryService {
    List<Athlete> handle(GetAllAthletesQuery query);
    Optional<Athlete> handle(GetAthleteByIdQuery query);
    Long getUserByAthleteId(Long AthleteId);
}
