package com.example.prueba_syman.model.dto;

import com.example.prueba_syman.entities.Material.EstadoMaterial;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MaterialDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre del material es obligatorio")
    @Size(max = 100, message = "El nombre del material no puede exceder 100 caracteres")
    private String nombre;
    
    private String descripcion;
    
    @NotBlank(message = "El tipo del material es obligatorio")
    @Size(max = 50, message = "El tipo del material no puede exceder 50 caracteres")
    private String tipo;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
    
    @NotNull(message = "La fecha de compra es obligatoria")
    @PastOrPresent(message = "La fecha de compra no puede ser futura")
    private LocalDate fechaCompra;
    
    private LocalDate fechaVenta;
    
    @NotNull(message = "El estado es obligatorio")
    private EstadoMaterial estado;
    
    @NotNull(message = "La ciudad es obligatoria")
    private CiudadDTO ciudad;
    
    // Constructors
    public MaterialDTO() {}
    
    public MaterialDTO(Long id, String nombre, String descripcion, String tipo, 
                      BigDecimal precio, LocalDate fechaCompra, LocalDate fechaVenta,
                      EstadoMaterial estado, CiudadDTO ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.fechaCompra = fechaCompra;
        this.fechaVenta = fechaVenta;
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
    
    public CiudadDTO getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(CiudadDTO ciudad) {
        this.ciudad = ciudad;
    }
}
