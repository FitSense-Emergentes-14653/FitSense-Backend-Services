package main.web.services.fitsense.iam.interfaces.rest.transform;

import main.web.services.fitsense.iam.domain.model.entities.Role;
import main.web.services.fitsense.iam.interfaces.rest.resources.RoleResource;

/**
 * Assembler class to convert Role entity to RoleResource.
 *
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}