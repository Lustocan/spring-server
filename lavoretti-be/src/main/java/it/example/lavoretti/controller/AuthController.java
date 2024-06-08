package it.example.lavoretti.controller;

import it.example.lavoretti.domain.auth.AuthRequest;
import it.example.lavoretti.domain.auth.AuthResponse;
import it.example.lavoretti.domain.users.SignUpUser;
import it.example.lavoretti.domain.users.User;
import it.example.lavoretti.service.users.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest,
                                                                HttpServletResponse res) {
        var authResponse = authService.login(authRequest, res);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<User> signUp(@RequestBody SignUpUser signUpUser) {
        var user = authService.signUp(signUpUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(user);
    }
}
