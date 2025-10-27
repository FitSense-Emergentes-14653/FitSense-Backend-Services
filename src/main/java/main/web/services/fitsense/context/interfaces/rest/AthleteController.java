package main.web.services.fitsense.context.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import main.web.services.fitsense.context.domain.model.commands.DeleteAthleteCommand;
import main.web.services.fitsense.context.domain.model.queries.GetAllAthletesQuery;
import main.web.services.fitsense.context.domain.model.queries.GetAthleteByIdQuery;
import main.web.services.fitsense.context.domain.services.AthleteCommandService;
import main.web.services.fitsense.context.domain.services.AthleteQueryService;
import main.web.services.fitsense.context.interfaces.rest.resources.AthleteResource;
import main.web.services.fitsense.context.interfaces.rest.resources.CreateAthleteResource;
import main.web.services.fitsense.context.interfaces.rest.resources.UpdateAthleteResource;
import main.web.services.fitsense.context.interfaces.rest.transform.AthleteResourceFromEntityAssembler;
import main.web.services.fitsense.context.interfaces.rest.transform.CreateAthleteCommandFromEntityAssembler;
import main.web.services.fitsense.context.interfaces.rest.transform.UpdateAthleteCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Athlete Controller
 *
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="/api/v1/Athletes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Athlete", description = "Athlete Management Endpoints")
public class AthleteController {

    private final AthleteCommandService AthleteCommandService;
    private final AthleteQueryService AthleteQueryService;

    public AthleteController(AthleteCommandService AthleteCommandService, AthleteQueryService AthleteQueryService) {
        this.AthleteCommandService = AthleteCommandService;
        this.AthleteQueryService = AthleteQueryService;
    }

    @PostMapping
    public ResponseEntity<AthleteResource> createAthlete(@RequestBody CreateAthleteResource resource) {
        var createAthleteCommand = CreateAthleteCommandFromEntityAssembler.toCommandFromResource(resource);
        var AthleteId = AthleteCommandService.handle(createAthleteCommand);
        if (AthleteId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getAthleteByIdQuery = new GetAthleteByIdQuery(AthleteId);
        var Athlete = AthleteQueryService.handle(getAthleteByIdQuery);
        if (Athlete.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var AthleteResource = AthleteResourceFromEntityAssembler.toResourceFromEntity(Athlete.get());
        return new ResponseEntity<>(AthleteResource, HttpStatus.CREATED);
    }

    /**
     * This method returns all the caretakers.
     * @return a list of caretaker resources
     */
    @GetMapping
    public ResponseEntity<List<AthleteResource>> getAllAthletes() {
        var getAllAthletesQuery = new GetAllAthletesQuery();
        var Athletes = AthleteQueryService.handle(getAllAthletesQuery);
        var AthleteResource = Athletes.stream().map(AthleteResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(AthleteResource);
    }

    @GetMapping(value = "/{athleteId}")
    public ResponseEntity<AthleteResource> getAthleteById(@PathVariable Long athleteId) {
        var getAthleteById = new GetAthleteByIdQuery(athleteId);
        var Athlete = AthleteQueryService.handle(getAthleteById);
        if (Athlete.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var AthleteResource = AthleteResourceFromEntityAssembler.toResourceFromEntity(Athlete.get());
        return ResponseEntity.ok(AthleteResource);
    }

    @PutMapping("/{athleteId}")
    public ResponseEntity<AthleteResource> updateAthlete(@PathVariable Long athleteId, @RequestBody UpdateAthleteResource resource) {
        var updateAthleteCommand = UpdateAthleteCommandFromResourceAssembler.toCommandFromResource(resource, athleteId);
        var updatedAthlete = AthleteCommandService.handle(updateAthleteCommand);
        if (updatedAthlete == 0L) return ResponseEntity.badRequest().build();
        var getAthleteByIdQuery = new GetAthleteByIdQuery(updatedAthlete);
        var Athlete = AthleteQueryService.handle(getAthleteByIdQuery);
        if (Athlete.isEmpty()) return ResponseEntity.badRequest().build();
        var AthleteResource = AthleteResourceFromEntityAssembler.toResourceFromEntity(Athlete.get());
        return ResponseEntity.ok(AthleteResource);
    }

    @DeleteMapping("/{athleteId}")
    public ResponseEntity<?> deleteAthlete(@PathVariable Long athleteId) {
        var deleteAthleteCommand = new DeleteAthleteCommand(athleteId);
        AthleteCommandService.handle(deleteAthleteCommand);
        return ResponseEntity.ok("Athlete with given id successfully deleted");
    }

}
