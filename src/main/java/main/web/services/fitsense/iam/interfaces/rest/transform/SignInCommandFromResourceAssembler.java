package main.web.services.fitsense.iam.interfaces.rest.transform;

import main.web.services.fitsense.iam.domain.model.commands.SignInCommand;
import main.web.services.fitsense.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembler to convert SignInResource to SignInCommand.
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.email(), signInResource.password());
    }
}