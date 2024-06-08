package it.example.lavoretti.config;

import it.example.lavoretti.details.UsrDetailsService;
import it.example.lavoretti.filter.JwtAuthFilter;
import it.example.lavoretti.mapper.UserMapperComponent;
import it.example.lavoretti.repository.UserRepository;
import javax.annotation.ParametersAreNonnullByDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class SecurityConfig {

    public static final String AUTH_URL = "auth/**";
    private final JwtAuthFilter authFilter;
    private final UserRepository userRepository;
    private final UserMapperComponent userMapperComponent;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).
                   authorizeHttpRequests(auth -> auth.requestMatchers(AUTH_URL).permitAll()
                                                     .anyRequest().authenticated()).
                   sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                   authenticationProvider(authenticationProvider()).
                   addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).
                   build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UsrDetailsService(userRepository, userMapperComponent);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
