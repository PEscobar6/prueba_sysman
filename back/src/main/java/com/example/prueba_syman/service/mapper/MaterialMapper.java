package com.example.prueba_syman.service.mapper;

import com.example.prueba_syman.entities.Ciudad;
import com.example.prueba_syman.entities.Departamento;
import com.example.prueba_syman.entities.Material;
import com.example.prueba_syman.model.dto.CiudadDTO;
import com.example.prueba_syman.model.dto.DepartamentoDTO;
import com.example.prueba_syman.model.dto.MaterialDTO;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {
    
    public MaterialDTO toDTO(Material material) {
        if (material == null) {
            return null;
        }
        
        DepartamentoDTO departamentoDTO = new DepartamentoDTO(
            material.getCiudad().getDepartamento().getCodigo(),
            material.getCiudad().getDepartamento().getNombre()
        );
        
        CiudadDTO ciudadDTO = new CiudadDTO(
            material.getCiudad().getCodigo(),
            material.getCiudad().getNombre(),
            departamentoDTO
        );
        
        return new MaterialDTO(
            material.getId(),
            material.getNombre(),
            material.getDescripcion(),
            material.getTipo(),
            material.getPrecio(),
            material.getFechaCompra(),
            material.getFechaVenta(),
            material.getEstado(),
            ciudadDTO
        );
    }
    
    public Material toEntity(MaterialDTO materialDTO) {
        if (materialDTO == null) {
            return null;
        }
        
        Departamento departamento = new Departamento(
            materialDTO.getCiudad().getDepartamento().getCodigo(),
            materialDTO.getCiudad().getDepartamento().getNombre()
        );
        
        Ciudad ciudad = new Ciudad(
            materialDTO.getCiudad().getCodigo(),
            materialDTO.getCiudad().getNombre(),
            departamento
        );
        
        Material material = new Material(
            materialDTO.getNombre(),
            materialDTO.getDescripcion(),
            materialDTO.getTipo(),
            materialDTO.getPrecio(),
            materialDTO.getFechaCompra(),
            materialDTO.getEstado(),
            ciudad
        );
        
        if (materialDTO.getId() != null) {
            material.setId(materialDTO.getId());
        }
        
        if (materialDTO.getFechaVenta() != null) {
            material.setFechaVenta(materialDTO.getFechaVenta());
        }
        
        return material;
    }
}
