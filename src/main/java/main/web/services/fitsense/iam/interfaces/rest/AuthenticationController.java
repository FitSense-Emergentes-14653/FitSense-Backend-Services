package main.web.services.fitsense.iam.interfaces.rest;
import io.swagger.v3.oas.annotations.tags.Tag;
import main.web.services.fitsense.iam.domain.services.UserCommandService;
import main.web.services.fitsense.iam.interfaces.rest.resources.*;
import main.web.services.fitsense.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import main.web.services.fitsense.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import main.web.services.fitsense.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import main.web.services.fitsense.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * AuthenticationController
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Handles the sign-in request.
     * @param signInResource the sign-in request body.
     * @return the authenticated user resource.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }
    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    /**
     * Handles password reset requests (simple flow).
     * Validates if the email exists and updates the password.
     *
     * @param resetPasswordResource the reset password request body.
     * @return confirmation message or error.
     */
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordResource resetPasswordResource) {
        boolean updated = userCommandService.resetPassword(resetPasswordResource.email(), resetPasswordResource.newPassword());
        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"El correo no está registrado.\"}");
        }
        return ResponseEntity.ok("{\"message\": \"Contraseña restablecida correctamente.\"}");
    }


}
