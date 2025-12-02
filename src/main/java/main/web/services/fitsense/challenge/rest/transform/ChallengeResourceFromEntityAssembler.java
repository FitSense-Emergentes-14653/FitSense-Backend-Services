package main.web.services.fitsense.challenge.rest.transform;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.web.services.fitsense.challenge.domain.model.entities.Challenge;
import main.web.services.fitsense.challenge.rest.resources.ChallengeResource;

/**
 * Assembler class to convert a Challenge entity (rutina generada por IA)
 * into a ChallengeResource for API responses.
 *
 * @author Fiorella
 * @version 1.1
 */
public class ChallengeResourceFromEntityAssembler {

    public static ChallengeResource toResourceFromEntity(Challenge entity) {
        Object jsonData;
        try {
            jsonData = new ObjectMapper().readValue(entity.getRutinaJson(), Object.class);
        } catch (Exception e) {
            jsonData = entity.getRutinaJson();
        }

        return new ChallengeResource(
                entity.getId(),
                entity.getUserId(),
                jsonData
        );
    }

}
