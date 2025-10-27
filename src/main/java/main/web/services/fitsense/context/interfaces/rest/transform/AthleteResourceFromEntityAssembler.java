package main.web.services.fitsense.context.interfaces.rest.transform;


import main.web.services.fitsense.context.domain.model.aggregates.Athlete;
import main.web.services.fitsense.context.interfaces.rest.resources.AthleteResource;

/**
 * Assembler class to transform Athlete entity to AthleteResource.
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public class AthleteResourceFromEntityAssembler {
    public static AthleteResource toResourceFromEntity(Athlete entity) {
        return new AthleteResource(
                entity.getId(),
                entity.getFullname().fullname(),
                entity.getPhone().phone(),
                entity.getUser().getId()
        );
    }
}
