package com.example.prueba_syman.controller;

import com.example.prueba_syman.model.dto.ApiResponseDTO;
import com.example.prueba_syman.model.dto.auth.JwtResponseDTO;
import com.example.prueba_syman.model.dto.auth.LoginRequestDTO;
import com.example.prueba_syman.model.dto.auth.UserInfoDTO;
import com.example.prueba_syman.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<JwtResponseDTO>> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            JwtResponseDTO jwtResponse = authService.authenticateUser(loginRequest);
            
            ApiResponseDTO<JwtResponseDTO> response = new ApiResponseDTO<>(
                    200,
                    "Login exitoso",
                    jwtResponse
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseDTO<JwtResponseDTO> response = new ApiResponseDTO<>(
                    401,
                    "Credenciales inválidas: " + e.getMessage(),
                    null
            );
            
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO<String>> logoutUser() {
        
        ApiResponseDTO<String> response = new ApiResponseDTO<>(
                200,
                "Logout exitoso",
                "Usuario desconectado correctamente"
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user")
    public ResponseEntity<ApiResponseDTO<UserInfoDTO>> getCurrentUser(Authentication authentication) {
        try {
            String username = authentication.getName();
            UserInfoDTO userInfo = authService.getCurrentUserInfo(username);
            
            ApiResponseDTO<UserInfoDTO> response = new ApiResponseDTO<>(
                    200,
                    "Información del usuario obtenida exitosamente",
                    userInfo
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseDTO<UserInfoDTO> response = new ApiResponseDTO<>(
                    404,
                    "Error al obtener información del usuario: " + e.getMessage(),
                    null
            );
            
            return ResponseEntity.status(404).body(response);
        }
    }
}
