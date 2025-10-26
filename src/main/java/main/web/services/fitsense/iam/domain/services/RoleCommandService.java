package main.web.services.fitsense.iam.domain.services;

import main.web.services.fitsense.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}