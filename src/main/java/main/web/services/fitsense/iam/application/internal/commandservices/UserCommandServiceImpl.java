package main.web.services.fitsense.iam.application.internal.commandservices;

import main.web.services.fitsense.iam.application.internal.outboundservices.hashing.HashingService;
import main.web.services.fitsense.iam.application.internal.outboundservices.tokens.TokenService;
import main.web.services.fitsense.iam.domain.model.aggregates.User;
import main.web.services.fitsense.iam.domain.model.commands.SignInCommand;
import main.web.services.fitsense.iam.domain.model.commands.SignUpCommand;
import main.web.services.fitsense.iam.domain.services.UserCommandService;
import main.web.services.fitsense.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import main.web.services.fitsense.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {
    private static final Logger logger = LoggerFactory.getLogger(UserCommandServiceImpl.class);

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the username and password
     * @return and optional containing the user matching the username and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        logger.debug("Attempting sign in for user: {}", command.email());
        var user = userRepository.findUserByEmailIs(command.email());

        if (user.isEmpty()) {
            logger.warn("Login attempt failed - User not found: {}", command.email());
            throw new RuntimeException("El correo electrónico no está registrado");
        }

        if (!hashingService.matches(command.password(), user.get().getPassword())) {
            logger.warn("Login attempt failed - Invalid password for user: {}", command.email());
            throw new RuntimeException("La contraseña ingresada es incorrecta");
        }

        logger.info("User successfully logged in: {}", command.email());
        var token = tokenService.generateToken(user.get().getEmail());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the user.
     * </p>
     * @param command the sign-up command containing the username and password
     * @return the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsUserByEmail(command.email()))
            throw new RuntimeException("Username already exists");
        var roles = command.roles().stream().map(role -> roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Role name not found"))).toList();
        var user = new User(command.email(), hashingService.encode(command.password()), roles);
        userRepository.save(user);
        return userRepository.findUserByEmailIs(command.email());
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        var userOpt = userRepository.findUserByEmailIs(email);
        if (userOpt.isEmpty()) {
            return false;
        }

        var user = userOpt.get();
        user.setPassword(hashingService.encode(newPassword));
        userRepository.save(user);
        return true;
    }

}