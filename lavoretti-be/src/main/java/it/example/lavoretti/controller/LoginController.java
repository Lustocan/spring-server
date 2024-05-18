package it.example.lavoretti.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/login")
public class LoginController {

    // hello
    @GetMapping
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Hello, World!");
    }

}
