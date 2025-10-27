package main.web.services.fitsense.context.application.internal.queryservices;

import main.web.services.fitsense.context.domain.model.aggregates.Athlete;
import main.web.services.fitsense.context.domain.model.queries.GetAllAthletesQuery;
import main.web.services.fitsense.context.domain.model.queries.GetAthleteByIdQuery;
import main.web.services.fitsense.context.domain.services.AthleteQueryService;
import main.web.services.fitsense.context.infraestructure.persistence.jpa.repositories.AthleteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * * Athlete Query Service Implementation
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
@Service
public class AthleteQueryServiceImpl implements AthleteQueryService {

    private final AthleteRepository AthleteRepository;

    public AthleteQueryServiceImpl(AthleteRepository AthleteRepository) {
        this.AthleteRepository = AthleteRepository;
    }

    @Override
    public List<Athlete> handle(GetAllAthletesQuery query) {
        return AthleteRepository.findAll();
    }

    @Override
    public Optional<Athlete> handle(GetAthleteByIdQuery query) {
        return AthleteRepository.findById(query.athleteId());
    }

    @Override
    public Long getUserByAthleteId(Long AthleteId) {
        Optional<Athlete> optionalAthlete = AthleteRepository.findById(AthleteId);
        return optionalAthlete.map(Athlete::getUserId).orElse(null);
    }
}
