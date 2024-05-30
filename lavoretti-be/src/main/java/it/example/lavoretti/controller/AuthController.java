package it.example.lavoretti.controller;

import it.example.lavoretti.helpers.PBDKF2Hashing;
import it.example.lavoretti.dao.AuthRequest;
import it.example.lavoretti.dao.UserEntity;
import it.example.lavoretti.domain.User;
import it.example.lavoretti.service.JwtService;
import it.example.lavoretti.service.UserService;
import it.example.lavoretti.mapper.UserMapperComponent;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private UserMapperComponent userMapperComponent;

    private SecureRandom random = new SecureRandom();

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired

    private JwtService jwtService;


    @PostMapping(path = "/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse res) throws Exception{
        User user = userService.findByUsername(authRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), PBDKF2Hashing.hashPassword(authRequest.getPassword(), user.salt().getBytes())));

        if(authentication.isAuthenticated()) {
            Cookie cookie = new Cookie("User", jwtService.generateToken(authRequest.getPassword()));
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60*60*40);
            res.addCookie(cookie);
            return jwtService.generateToken(authRequest.getUsername());
        }
        else throw new UsernameNotFoundException("Wrong Credentials");
    }

    @PostMapping(path = "/signUp")
    public ResponseEntity<User> signUp(@RequestBody User user) throws Exception {
        if(userService.findByUsername(user.username())!=null||
           userService.findByEmail(user.email())!=null)
           return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        UserEntity usr = userMapperComponent.toEntity(user);
        byte[] salt = PBDKF2Hashing.getSalt();
        usr.setSalt(salt.toString());
        usr.setPassword(passwordEncoder.encode(PBDKF2Hashing.hashPassword(usr.getPassword(), usr.getSalt().getBytes())));

        user = userMapperComponent.toDomain(usr);
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }
}
