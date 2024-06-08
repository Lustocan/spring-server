package it.example.lavoretti.service.users;

import it.example.lavoretti.dao.UserEntity;
import it.example.lavoretti.domain.users.User;
import it.example.lavoretti.exception.UserNotFoundException;
import it.example.lavoretti.mapper.UserMapperComponent;
import it.example.lavoretti.repository.UserRepository;
import jakarta.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class UserService {

    private final UserMapperComponent userMapperComponent;
    private final UserRepository userRepository;

    public User saveUser(User user) {
        var userEntity = userMapperComponent.toEntity(user);
        boolean isEmailTaken = userRepository.findByEmail(userEntity.getEmail())
                                             .isPresent();
        if (isEmailTaken) {
            throw new IllegalArgumentException("Email is already taken");
        }
        boolean isUsernameTaken = userRepository.findByUsername(userEntity.getUsername())
                                                .isPresent();
        if (isUsernameTaken) {
            throw new IllegalArgumentException("Username is already taken");
        }
        userRepository.save(userEntity);
        return userMapperComponent.toDomain(userEntity);
    }

    @Nonnull
    public User findByUserId(UUID userId) {
        return userRepository.findById(userId)
                             .map(userMapperComponent::toDomain)
                             .orElseThrow(() -> new UserNotFoundException("User not found " + userId));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                             .map(userMapperComponent::toDomain)
                             .orElseThrow(() -> new UserNotFoundException("User not found. username = " + username));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                             .map(userMapperComponent::toDomain);
    }


    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
