package main.web.services.fitsense.iam.application.internal.queryservices;


import main.web.services.fitsense.iam.domain.model.aggregates.User;
import main.web.services.fitsense.iam.domain.model.queries.GetAllUsersQuery;
import main.web.services.fitsense.iam.domain.model.queries.GetUserByEmailQuery;
import main.web.services.fitsense.iam.domain.model.queries.GetUserByIdQuery;
import main.web.services.fitsense.iam.domain.services.UserQueryService;
import main.web.services.fitsense.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link UserQueryService} interface.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findUserByEmailIs(query.email());
    }
}