package main.web.services.fitsense.iam.domain.model.commands;


import main.web.services.fitsense.iam.domain.model.entities.Role;

import java.util.List;

/**
 * @author Fiorella Jarama Peñaloza - u202120418
 * @version 1.0
 */
public record SignUpCommand(String email, String password, List<Role> roles) {
}
