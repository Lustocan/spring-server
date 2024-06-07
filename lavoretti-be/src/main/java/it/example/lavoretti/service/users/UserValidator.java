package it.example.lavoretti.service.users;

import it.example.lavoretti.domain.users.SignUpUser;
import it.example.lavoretti.exception.EmailNotAvailableException;
import it.example.lavoretti.exception.UserNameNotAvailableException;
import it.example.lavoretti.repository.UserRepository;
import javax.annotation.ParametersAreNonnullByDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class UserValidator {

    private final UserRepository userRepository;

    public void validateSignUp(SignUpUser user) {
        var userNameExists = userRepository.findByUsername(user.username());
        if (userNameExists.isPresent()) {
            throw new UserNameNotAvailableException(user.username());
        }
        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new EmailNotAvailableException(user.email());
        }
    }
}
