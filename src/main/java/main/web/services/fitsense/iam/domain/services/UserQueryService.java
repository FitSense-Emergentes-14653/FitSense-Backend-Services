package main.web.services.fitsense.iam.domain.services;

import main.web.services.fitsense.iam.domain.model.aggregates.User;
import main.web.services.fitsense.iam.domain.model.queries.GetAllUsersQuery;
import main.web.services.fitsense.iam.domain.model.queries.GetUserByEmailQuery;
import main.web.services.fitsense.iam.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * UserQueryService interface for handling user-related queries.
 * @author Fiorella Jarama Peñaloza
 * @version 1.0
 */

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
}