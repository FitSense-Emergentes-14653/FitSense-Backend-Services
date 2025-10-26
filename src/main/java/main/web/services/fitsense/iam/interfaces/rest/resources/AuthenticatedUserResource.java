package main.web.services.fitsense.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String email, String token) {
}