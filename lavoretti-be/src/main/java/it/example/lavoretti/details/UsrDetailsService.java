package it.example.lavoretti.details;

import it.example.lavoretti.domain.User;
import it.example.lavoretti.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsrDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> user = Optional.ofNullable(repository.findByUsername(username));

        return user.map(UsrDetails::new).
               orElseThrow(()-> new UsernameNotFoundException("User not found "+username));
    }
}