package main.web.services.fitsense.iam.interfaces.rest.transform;

import main.web.services.fitsense.iam.domain.model.aggregates.User;
import main.web.services.fitsense.iam.domain.model.entities.Role;
import main.web.services.fitsense.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId(), user.getEmail(), roles);
    }
}
