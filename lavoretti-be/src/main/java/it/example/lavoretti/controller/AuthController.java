package it.example.lavoretti.controller;

import it.example.lavoretti.dao.UserEntity;
import it.example.lavoretti.domain.User;
import it.example.lavoretti.service.UserService;
import it.example.lavoretti.mapper.UserMapperComponent;
import it.example.lavoretti.helpers.PBDKF2Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapperComponent userMapperComponent;

    private SecureRandom random = new SecureRandom();


    @PostMapping(path = "/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Hello, World!");
    }

    @PostMapping(path = "/signUp")
    public ResponseEntity<User> signUp(@RequestBody User user) throws Exception {
        if(userService.findByUsername(user.username())!=null||
           userService.findByEmail(user.email())!=null)
           return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        UserEntity usr = userMapperComponent.toEntity(user);
        byte[] salt = PBDKF2Hashing.getSalt();
        usr.setSalt(salt.toString());
        usr.setPassword(PBDKF2Hashing.hashPassword(usr.getPassword(), salt));

        user = userMapperComponent.toDomain(usr);
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }
}
