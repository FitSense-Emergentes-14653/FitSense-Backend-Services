package main.web.services.fitsense.context.application.internal.commandservices;

import main.web.services.fitsense.context.domain.exceptions.AthleteNotFoundException;
import main.web.services.fitsense.context.domain.exceptions.AthleteSaveException;
import main.web.services.fitsense.context.domain.model.aggregates.Athlete;
import main.web.services.fitsense.context.domain.model.commands.CreateAthleteCommand;
import main.web.services.fitsense.context.domain.model.commands.DeleteAthleteCommand;
import main.web.services.fitsense.context.domain.model.commands.UpdateAthleteCommand;
import main.web.services.fitsense.context.domain.services.AthleteCommandService;
import main.web.services.fitsense.context.infraestructure.persistence.jpa.repositories.AthleteRepository;
import main.web.services.fitsense.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Implementation of the AthleteCommandService interface.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
@Service
public class AthleteCommandServiceImpl implements AthleteCommandService {

    private final AthleteRepository AthleteRepository;
    private final UserRepository userRepository;

    public AthleteCommandServiceImpl(AthleteRepository AthleteRepository, UserRepository userRepository) {
        this.AthleteRepository = AthleteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long handle(CreateAthleteCommand command) {
        var user = userRepository.findById(command.userId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        var Athlete = new Athlete(command, user.get());
        try {
            AthleteRepository.save(Athlete);
        } catch (Exception e) {
            throw new AthleteSaveException("Error while saving Athlete: ",e);
        }
        return Athlete.getId();
    }

    @Override
    public Long handle(UpdateAthleteCommand command) {
        var existingAthlete = AthleteRepository.findById(command.athleteId());
        if (existingAthlete.isEmpty()) {
            throw new IllegalArgumentException("Athlete does not exist");
        }
        var athlete = existingAthlete.get();
        athlete.updateAthlete(
                command.fullname(),
                command.phone(),
                command.gender(),
                command.age(),
                command.weight(),
                command.height(),
                command.goal(),
                command.activityLevel(),
                command.equipment()
        );

        try {
            AthleteRepository.save(athlete);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating Athlete: " + e.getMessage());
        }
        return athlete.getId();
    }

    @Override
    public Optional<Athlete> handle(DeleteAthleteCommand command) {
        if (!AthleteRepository.existsById(command.athleteId())) {
            throw new AthleteNotFoundException(command.athleteId());
        }
        var Athlete = AthleteRepository.findById(command.athleteId());
        Athlete.ifPresent(AthleteRepository::delete);
        return Athlete;
    }
}
