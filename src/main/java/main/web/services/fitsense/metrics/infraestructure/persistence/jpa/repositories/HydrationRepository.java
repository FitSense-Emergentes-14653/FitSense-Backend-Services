package main.web.services.fitsense.metrics.infraestructure.persistence.jpa.repositories;

import main.web.services.fitsense.metrics.domain.model.aggregates.Hydration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HydrationRepository extends JpaRepository<Hydration, Long> {
}
