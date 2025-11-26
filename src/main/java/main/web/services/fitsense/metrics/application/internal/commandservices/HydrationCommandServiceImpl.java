package main.web.services.fitsense.metrics.application.internal.commandservices;

import main.web.services.fitsense.context.infraestructure.persistence.jpa.repositories.AthleteRepository;
import main.web.services.fitsense.metrics.domain.model.aggregates.Hydration;
import main.web.services.fitsense.metrics.domain.model.commands.CreateHydrationCommand;
import main.web.services.fitsense.metrics.domain.model.commands.UpdateHydrationCommand;
import main.web.services.fitsense.metrics.domain.services.HydrationCommandService;
import main.web.services.fitsense.metrics.infraestructure.persistence.jpa.repositories.HydrationRepository;
import org.springframework.stereotype.Service;

@Service
public class HydrationCommandServiceImpl implements HydrationCommandService {

    private final HydrationRepository hydrationRepository;
    private final AthleteRepository athleteRepository;


    public HydrationCommandServiceImpl(HydrationRepository hydrationRepository, AthleteRepository athleteRepository) {
        this.hydrationRepository = hydrationRepository;
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Long handle(CreateHydrationCommand command) {
        var athlete = athleteRepository.findById(command.athleteId())
                .orElseThrow(() -> new RuntimeException("Athlete not found"));
        var hydration = new Hydration(command, athlete);
        hydrationRepository.save(hydration);
        return hydration.getId();
    }


    @Override
    public Long handle(UpdateHydrationCommand command) {
        var hydration = hydrationRepository.findById(command.hydrationId())
                .orElseThrow(() -> new RuntimeException("Hydration record not found"));
        hydration.addWater(command.quantity());
        hydrationRepository.save(hydration);
        return hydration.getId();
    }
}
