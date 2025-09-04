package com.example.prueba_syman.service;

import com.example.prueba_syman.entities.Usuario;
import com.example.prueba_syman.model.dto.auth.JwtResponseDTO;
import com.example.prueba_syman.model.dto.auth.LoginRequestDTO;
import com.example.prueba_syman.model.dto.auth.UserInfoDTO;
import com.example.prueba_syman.repository.UsuarioRepository;
import com.example.prueba_syman.security.jwt.JwtUtils;
import com.example.prueba_syman.security.services.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class AuthService {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    JwtUtils jwtUtils;
    
    public JwtResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(), 
                        loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        // Actualizar último acceso
        usuarioRepository.updateUltimoAcceso(userPrincipal.getUsername(), LocalDateTime.now());
        
        // Obtener fecha de expiración del token
        LocalDateTime fechaExpiracion = jwtUtils.getExpirationDateFromJwtToken(jwt);
        
        return new JwtResponseDTO(jwt,
                userPrincipal.getId(),
                userPrincipal.getUsername(),
                userPrincipal.getEmail(),
                userPrincipal.getNombreCompleto(),
                getUserRoles(userPrincipal.getUsername()),
                fechaExpiracion);
    }
    
    private java.util.Set<Usuario.Rol> getUserRoles(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);
        return usuario != null ? usuario.getRoles() : java.util.Set.of();
    }
    
    public UserInfoDTO getCurrentUserInfo(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        
        return new UserInfoDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getNombreCompleto(),
                usuario.getTelefono(),
                usuario.getEstado(),
                usuario.getRoles(),
                usuario.getUltimoAcceso(),
                usuario.getFechaCreacion()
        );
    }
}
