package main.web.services.fitsense.iam.interfaces.rest.resources;

import java.util.List;

/**
 * UserResource - Resource representation of a User.
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */
public record UserResource(Long id, String email, List<String> roles) {
}