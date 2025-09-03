package com.example.prueba_syman.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CiudadDTO {
    
    @NotBlank(message = "El código de la ciudad es obligatorio")
    @Size(max = 10, message = "El código de la ciudad no puede exceder 10 caracteres")
    private String codigo;
    
    @NotBlank(message = "El nombre de la ciudad es obligatorio")
    @Size(max = 100, message = "El nombre de la ciudad no puede exceder 100 caracteres")
    private String nombre;
    
    @NotNull(message = "El departamento es obligatorio")
    private DepartamentoDTO departamento;
    
    // Constructors
    public CiudadDTO() {}
    
    public CiudadDTO(String codigo, String nombre, DepartamentoDTO departamento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.departamento = departamento;
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
    
    public DepartamentoDTO getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }
}
