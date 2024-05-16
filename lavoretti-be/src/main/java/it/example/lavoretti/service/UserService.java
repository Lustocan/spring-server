package it.example.lavoretti.service;

import it.example.lavoretti.dao.UserId;
import it.example.lavoretti.domain.User;
import it.example.lavoretti.exception.UserNotFoundException;
import it.example.lavoretti.mapper.UserMapperComponent;
import it.example.lavoretti.repository.UserRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapperComponent userMapperComponent;
    private final UserRepository userRepository;

    @Nonnull
    public User saveUser(@Nonnull User user) {
        var userEntity = userMapperComponent.toEntity(user);
        userRepository.save(userEntity);
        return userMapperComponent.toDomain(userEntity);
    }

    @Nonnull
    public User findByUserId(@Nonnull UserId userId) {
        return userRepository.findById(userId)
                             .map(userMapperComponent::toDomain)
                             .orElseThrow(() -> new UserNotFoundException("User not found " + userId));
    }

}
