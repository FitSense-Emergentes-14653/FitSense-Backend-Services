package main.web.services.fitsense.metrics.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import main.web.services.fitsense.context.infraestructure.persistence.jpa.repositories.AthleteRepository;
import main.web.services.fitsense.metrics.domain.model.aggregates.Hydration;
import main.web.services.fitsense.metrics.domain.valueobjects.Quantity;
import main.web.services.fitsense.metrics.infraestructure.persistence.jpa.repositories.HydrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Controller for hydration tracking and goal management.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/hydration", produces = "application/json")
@Tag(name = "Hydration", description = "Endpoints for hydration tracking and goals")
public class HydrationController {

    private final HydrationRepository hydrationRepository;
    private final AthleteRepository athleteRepository;

    public HydrationController(HydrationRepository hydrationRepository, AthleteRepository athleteRepository) {
        this.hydrationRepository = hydrationRepository;
        this.athleteRepository = athleteRepository;
    }

    @PutMapping("/goal/{athleteId}")
    public ResponseEntity<String> updateHydrationGoal(@PathVariable Long athleteId, @RequestParam Double hydrationGoal) {
        var athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        Optional<Hydration> hydrationOpt = hydrationRepository
                .findAll()
                .stream()
                .filter(h -> h.getAthlete().getId().equals(athleteId)
                        && h.getCreatedAt()
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate()
                        .equals(LocalDate.now()))
                .findFirst();

        Hydration hydration;
        if (hydrationOpt.isPresent()) {
            hydration = hydrationOpt.get();
            hydration.setHydrationGoal(hydrationGoal);
        } else {
            hydration = new Hydration();
            hydration.setAthlete(athlete);
            hydration.setHydrationGoal(hydrationGoal);
            hydration.setQuantity(new Quantity(0.0));
        }

        hydrationRepository.save(hydration);
        return ResponseEntity.ok("Hydration goal set to " + hydrationGoal + " ml for today.");
    }

    @PostMapping("/{athleteId}")
    public ResponseEntity<?> addWater(@PathVariable Long athleteId, @RequestParam Double amount) {
        var athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        Optional<Hydration> hydrationOpt = hydrationRepository
                .findAll()
                .stream()
                .filter(h -> h.getAthlete().getId().equals(athleteId)
                        && h.getCreatedAt()
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate()
                        .equals(LocalDate.now()))
                .findFirst();

        Hydration hydration;
        if (hydrationOpt.isPresent()) {
            hydration = hydrationOpt.get();
            hydration.addWater(amount);
        } else {
            hydration = new Hydration();
            hydration.setAthlete(athlete);
            hydration.setQuantity(new Quantity(amount));
            hydration.setHydrationGoal(2000.0); // meta por defecto si no se definió
        }

        hydrationRepository.save(hydration);

        double progress = hydration.progressPercentage();
        boolean goalReached = hydration.goalReached();

        return ResponseEntity.ok(String.format(
                "Total: %.0f ml | Goal: %.0f ml | Progress: %.1f%% | Goal reached: %s",
                hydration.getQuantity().quantity(),
                hydration.getHydrationGoal(),
                progress,
                goalReached ? "✅" : "❌"
        ));
    }

    @GetMapping("/{athleteId}")
    public ResponseEntity<?> getTodayHydration(@PathVariable Long athleteId) {
        Optional<Hydration> hydrationOpt = hydrationRepository
                .findAll()
                .stream()
                .filter(h -> h.getAthlete().getId().equals(athleteId)
                        && h.getCreatedAt()
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate()
                        .equals(LocalDate.now()))
                .findFirst();

        if (hydrationOpt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("No hydration record found for athlete " + athleteId + " today.");
        }

        var hydration = hydrationOpt.get();

        double total = hydration.getQuantity().quantity();
        double goal = hydration.getHydrationGoal();
        double progress = hydration.progressPercentage();
        boolean goalReached = hydration.goalReached();

        var response = String.format(
                "Athlete %d → Total: %.0f ml | Goal: %.0f ml | Progress: %.1f%% | Goal reached: %s",
                athleteId,
                total,
                goal,
                progress,
                goalReached ? "✅" : "❌"
        );

        return ResponseEntity.ok(response);
    }

}
