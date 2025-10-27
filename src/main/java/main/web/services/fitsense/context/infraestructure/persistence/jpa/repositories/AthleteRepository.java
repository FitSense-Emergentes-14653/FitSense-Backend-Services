package main.web.services.fitsense.context.infraestructure.persistence.jpa.repositories;

import main.web.services.fitsense.context.domain.model.aggregates.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Athlete entities in the database.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
