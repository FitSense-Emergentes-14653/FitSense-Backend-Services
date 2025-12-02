package main.web.services.fitsense.challenge.repositories;

import main.web.services.fitsense.challenge.domain.model.entities.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Rutinas
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByUserIdOrderByCreatedAtDesc(String userId);
    Optional<Challenge> findTopByUserIdOrderByCreatedAtDesc(String userId);

}
