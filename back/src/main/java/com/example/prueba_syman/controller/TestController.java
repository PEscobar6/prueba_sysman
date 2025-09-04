package com.example.prueba_syman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/hash/{password}")
    public String generateHash(@PathVariable String password) {
        String hash = passwordEncoder.encode(password);
        return "Password: " + password + "<br>Hash: " + hash + "<br>Verification: " + passwordEncoder.matches(password, hash);
    }
}
