package it.example.lavoretti.service.users;

import it.example.lavoretti.dao.UserEntity;
import it.example.lavoretti.domain.auth.AuthRequest;
import it.example.lavoretti.domain.auth.AuthResponse;
import it.example.lavoretti.domain.users.SignUpUser;
import it.example.lavoretti.domain.users.User;
import it.example.lavoretti.exception.PasswordEncoderException;
import it.example.lavoretti.helpers.PBDKF2Hashing;
import it.example.lavoretti.mapper.UserMapperComponent;
import it.example.lavoretti.repository.UserRepository;
import it.example.lavoretti.service.auth.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import javax.annotation.ParametersAreNonnullByDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapperComponent userMapperComponent;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public AuthResponse login(AuthRequest authRequest, HttpServletResponse res) {
        User user = userService.getByUsername(authRequest.username());
        var encodedPassword = encodePassword(authRequest.password(), user.salt().getBytes());
        var auth = new UsernamePasswordAuthenticationToken(authRequest.username(),
                                                           encodedPassword);
        Authentication authentication = authenticationManager.authenticate(auth);
        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Wrong Credentials");
        }
        var token = jwtService.generateToken(authRequest.username());
        generateCookie(token, res);
        return new AuthResponse(token);
    }

    public User signUp(SignUpUser signUpUser) {
        userValidator.validateSignUp(signUpUser);
        byte[] salt = PBDKF2Hashing.getSalt();
        var encodedPassword = encodePassword(signUpUser.password(), salt);
        UserEntity usr = userMapperComponent.toEntity(signUpUser, createSalt(salt));
        usr.setPassword(encodedPassword);
        userRepository.save(usr);
        return userMapperComponent.toDomain(usr);
    }

    private void generateCookie(String token, HttpServletResponse res) {
        Cookie cookie = new Cookie("User", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 40);
        res.addCookie(cookie);
    }

    private String createSalt(byte[] salt) {
        return new String(salt, StandardCharsets.UTF_8);
    }

    private String encodePassword(String password,
                                  byte[] salt) {
        try {
            return passwordEncoder.encode(PBDKF2Hashing.hashPassword(password, salt));
        } catch (Exception e) {
            throw new PasswordEncoderException("Error while encoding password");
        }
    }
}
