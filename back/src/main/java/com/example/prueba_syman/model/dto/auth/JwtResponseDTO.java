package com.example.prueba_syman.model.dto.auth;

import com.example.prueba_syman.entities.Usuario;

import java.time.LocalDateTime;
import java.util.Set;

public class JwtResponseDTO {
    
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String nombreCompleto;
    private Set<Usuario.Rol> roles;
    private LocalDateTime fechaExpiracion;
    
    // Constructors
    public JwtResponseDTO() {}
    
    public JwtResponseDTO(String token, Long id, String username, String email, 
                         String nombreCompleto, Set<Usuario.Rol> roles, LocalDateTime fechaExpiracion) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.roles = roles;
        this.fechaExpiracion = fechaExpiracion;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public Set<Usuario.Rol> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Usuario.Rol> roles) {
        this.roles = roles;
    }
    
    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }
    
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}
