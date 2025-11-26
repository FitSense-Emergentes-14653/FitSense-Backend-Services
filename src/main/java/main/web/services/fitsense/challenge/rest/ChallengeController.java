package main.web.services.fitsense.challenge.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import main.web.services.fitsense.challenge.domain.model.entities.Challenge;
import main.web.services.fitsense.challenge.repositories.ChallengeRepository;
import main.web.services.fitsense.challenge.rest.resources.ChallengeResource;
import main.web.services.fitsense.challenge.rest.transform.ChallengeResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user-generated challenges (AI-generated routines).
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.1
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/challenges", produces = "application/json")
@Tag(name = "Challenges", description = "Endpoints for AI-generated routines (user challenges)")
public class ChallengeController {

    private final ChallengeRepository challengeRepository;

    public ChallengeController(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChallengeResource>> getChallengesByUser(@PathVariable String userId) {
        List<Challenge> challenges = challengeRepository.findByUserId(userId);
        if (challenges.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resources = challenges.stream()
                .map(ChallengeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResource> getChallengeById(@PathVariable Long id) {
        return challengeRepository.findById(id)
                .map(ChallengeResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
