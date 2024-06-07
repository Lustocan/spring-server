package it.example.lavoretti.details;

import it.example.lavoretti.domain.users.CustomUserDetails;
import it.example.lavoretti.mapper.UserMapperComponent;
import it.example.lavoretti.repository.UserRepository;
import javax.annotation.ParametersAreNonnullByDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class UsrDetailsService implements UserDetailsService {

    private final UserRepository repository;

    private final UserMapperComponent userMapperComponent;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                         .map(userMapperComponent::toDomain)
                         .map(CustomUserDetails::new)
                         .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
