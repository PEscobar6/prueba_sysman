package com.example.prueba_syman.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123";

        // Generar nuevo hash
        String newHash = encoder.encode(rawPassword);
        System.out.println("Raw password: " + rawPassword);
        System.out.println("New BCrypt hash: " + newHash);

        // Verificar que el nuevo hash funciona
        boolean matches = encoder.matches(rawPassword, newHash);
        System.out.println("Hash verification: " + matches);

        // Verificar el hash actual de la BD
        String currentHash = "$2a$10$GRyPPSN3PYrPOBU0kksKbu2SXyQhzNDQDqvXHXX98C5IpqOBLOTdq";
        boolean currentMatches = encoder.matches(rawPassword, currentHash);
        System.out.println("Current hash verification: " + currentMatches);
    }
}
