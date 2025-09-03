package com.example.prueba_syman.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "departamentos")
public class Departamento {
    
    @Id
    @Column(length = 10)
    private String codigo;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    // Constructors
    public Departamento() {}
    
    public Departamento(String codigo, String nombre) {
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
