package com.example.prueba_syman.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartamentoDTO {
    
    @NotBlank(message = "El código del departamento es obligatorio")
    @Size(max = 10, message = "El código del departamento no puede exceder 10 caracteres")
    private String codigo;
    
    @NotBlank(message = "El nombre del departamento es obligatorio")
    @Size(max = 100, message = "El nombre del departamento no puede exceder 100 caracteres")
    private String nombre;
    
    // Constructors
    public DepartamentoDTO() {}
    
    public DepartamentoDTO(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    
    // Getters and Setters
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
