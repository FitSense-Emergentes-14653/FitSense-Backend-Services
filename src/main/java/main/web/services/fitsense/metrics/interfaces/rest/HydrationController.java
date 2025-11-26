package main.web.services.fitsense.metrics.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import main.web.services.fitsense.context.infraestructure.persistence.jpa.repositories.AthleteRepository;
import main.web.services.fitsense.metrics.domain.model.aggregates.Hydration;
import main.web.services.fitsense.metrics.domain.valueobjects.Quantity;
import main.web.services.fitsense.metrics.infraestructure.persistence.jpa.repositories.HydrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for hydration tracking and goal management.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.2
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
    public ResponseEntity<Map<String, Object>> updateHydrationGoal(
            @PathVariable Long athleteId,
            @RequestParam Double hydrationGoal) {

        var athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        Optional<Hydration> hydrationOpt = hydrationRepository
                .findAll()
                .stream()
                .filter(h -> h.getAthlete().getId().equals(athleteId)
                        && h.getCreatedAt().toInstant()
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
        return ResponseEntity.ok(buildHydrationResponse(hydration));
    }

    @PostMapping("/{athleteId}")
    public ResponseEntity<Map<String, Object>> addWater(
            @PathVariable Long athleteId,
            @RequestParam Double amount) {

        var athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        Optional<Hydration> hydrationOpt = hydrationRepository
                .findAll()
                .stream()
                .filter(h -> h.getAthlete().getId().equals(athleteId)
                        && h.getCreatedAt().toInstant()
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
            hydration.setHydrationGoal(2000.0); // Default if not defined
        }

        hydrationRepository.save(hydration);
        return ResponseEntity.ok(buildHydrationResponse(hydration));
    }

    @GetMapping("/{athleteId}")
    public ResponseEntity<Map<String, Object>> getTodayHydration(@PathVariable Long athleteId) {

        Optional<Hydration> hydrationOpt = hydrationRepository
                .findAll()
                .stream()
                .filter(h -> h.getAthlete().getId().equals(athleteId)
                        && h.getCreatedAt().toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate()
                        .equals(LocalDate.now()))
                .findFirst();

        if (hydrationOpt.isEmpty()) {
            Map<String, Object> empty = new HashMap<>();
            empty.put("athleteId", athleteId);
            empty.put("total", 0);
            empty.put("hydrationGoal", 2000);
            empty.put("progress", 0);
            empty.put("goalReached", false);
            return ResponseEntity.ok(empty);
        }

        Hydration hydration = hydrationOpt.get();
        return ResponseEntity.ok(buildHydrationResponse(hydration));
    }

    private Map<String, Object> buildHydrationResponse(Hydration hydration) {
        Map<String, Object> map = new HashMap<>();
        map.put("athleteId", hydration.getAthlete().getId());
        map.put("total", hydration.getQuantity().quantity());
        map.put("hydrationGoal", hydration.getHydrationGoal());
        map.put("progress", hydration.progressPercentage());
        map.put("goalReached", hydration.goalReached());
        return map;
    }
}
