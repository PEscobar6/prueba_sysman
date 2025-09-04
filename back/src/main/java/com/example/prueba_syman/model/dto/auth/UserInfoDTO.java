package com.example.prueba_syman.model.dto.auth;

import com.example.prueba_syman.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;

public class UserInfoDTO {
    private Long id;
    private String username;
    private String email;
    private String nombreCompleto;
    private String telefono;
    private Usuario.EstadoUsuario estado;
    private Set<Usuario.Rol> roles;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ultimoAcceso;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;

    public UserInfoDTO() {}

    public UserInfoDTO(Long id, String username, String email, String nombreCompleto, 
                      String telefono, Usuario.EstadoUsuario estado, Set<Usuario.Rol> roles, 
                      LocalDateTime ultimoAcceso, LocalDateTime fechaCreacion) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.estado = estado;
        this.roles = roles;
        this.ultimoAcceso = ultimoAcceso;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Usuario.EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(Usuario.EstadoUsuario estado) {
        this.estado = estado;
    }

    public Set<Usuario.Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Usuario.Rol> roles) {
        this.roles = roles;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
