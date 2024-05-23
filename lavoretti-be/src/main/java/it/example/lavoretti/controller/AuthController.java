package it.example.lavoretti.controller;

import it.example.lavoretti.domain.User;
import it.example.lavoretti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Hello, World!");
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody User user){
        User currentUser = userService.saveUser(user);
        if(currentUser==null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(currentUser, HttpStatus.CREATED);
    }
}
