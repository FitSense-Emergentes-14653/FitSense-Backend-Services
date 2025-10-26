package main.web.services.fitsense.iam.domain.services;

import main.web.services.fitsense.iam.domain.model.entities.Role;
import main.web.services.fitsense.iam.domain.model.queries.GetAllRolesQuery;
import main.web.services.fitsense.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}