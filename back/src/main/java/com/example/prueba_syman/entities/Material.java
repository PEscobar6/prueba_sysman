package com.example.prueba_syman.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "materiales")
public class Material {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(nullable = false, length = 50)
    private String tipo;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;
    
    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoMaterial estado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_codigo", nullable = false)
    private Ciudad ciudad;
    
    public enum EstadoMaterial {
        ACTIVO, DISPONIBLE, ASIGNADO
    }
    
    // Constructors
    public Material() {}
    
    public Material(String nombre, String descripcion, String tipo, BigDecimal precio, 
                   LocalDate fechaCompra, EstadoMaterial estado, Ciudad ciudad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.ciudad = ciudad;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public LocalDate getFechaCompra() {
        return fechaCompra;
    }
    
    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    
    public LocalDate getFechaVenta() {
        return fechaVenta;
    }
    
    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    public EstadoMaterial getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoMaterial estado) {
        this.estado = estado;
    }
    
    public Ciudad getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
