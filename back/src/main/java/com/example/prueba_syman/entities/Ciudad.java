package com.example.prueba_syman.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ciudades")
public class Ciudad {
    
    @Id
    @Column(length = 10)
    private String codigo;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_codigo", nullable = false)
    private Departamento departamento;
    
    // Constructors
    public Ciudad() {}
    
    public Ciudad(String codigo, String nombre, Departamento departamento) {
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
    
    public Departamento getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
