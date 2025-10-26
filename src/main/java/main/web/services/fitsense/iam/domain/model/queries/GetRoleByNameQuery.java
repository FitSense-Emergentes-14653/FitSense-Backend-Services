package main.web.services.fitsense.iam.domain.model.queries;
import main.web.services.fitsense.iam.domain.model.valueobjects.Roles;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */

public record GetRoleByNameQuery(Roles name) {
}
