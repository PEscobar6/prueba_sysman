package com.example.prueba_syman.service;

import com.example.prueba_syman.entities.Ciudad;
import com.example.prueba_syman.entities.Material;
import com.example.prueba_syman.exception.CiudadNotFoundException;
import com.example.prueba_syman.exception.InvalidDateException;
import com.example.prueba_syman.exception.MaterialNotFoundException;
import com.example.prueba_syman.model.dto.MaterialDTO;
import com.example.prueba_syman.repository.CiudadRepository;
import com.example.prueba_syman.repository.MaterialRepository;
import com.example.prueba_syman.service.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MaterialService {
    
    @Autowired
    private MaterialRepository materialRepository;
    
    @Autowired
    private CiudadRepository ciudadRepository;
    
    @Autowired
    private MaterialMapper materialMapper;
    
    public List<MaterialDTO> findAll() {
        List<Material> materiales = materialRepository.findAllOrderedById();
        return materiales.stream()
                .map(materialMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public MaterialDTO findById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException("Material no encontrado con ID: " + id));
        return materialMapper.toDTO(material);
    }
    
    public List<MaterialDTO> findByTipo(String tipo) {
        List<Material> materiales = materialRepository.findByTipo(tipo);
        return materiales.stream()
                .map(materialMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<MaterialDTO> findByFechaCompra(LocalDate fechaCompra) {
        List<Material> materiales = materialRepository.findByFechaCompra(fechaCompra);
        return materiales.stream()
                .map(materialMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<MaterialDTO> findByTipoAndFechaCompra(String tipo, LocalDate fechaCompra) {
        List<Material> materiales = materialRepository.findByTipoAndFechaCompra(tipo, fechaCompra);
        return materiales.stream()
                .map(materialMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<MaterialDTO> findByCiudad(String ciudadIdentifier) {
        List<Material> materiales;
        
        // Intentar buscar por código primero, luego por nombre
        materiales = materialRepository.findByCiudadCodigo(ciudadIdentifier);
        if (materiales.isEmpty()) {
            materiales = materialRepository.findByCiudadNombre(ciudadIdentifier);
        }
        
        return materiales.stream()
                .map(materialMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public MaterialDTO create(MaterialDTO materialDTO) {
        validateDates(materialDTO.getFechaCompra(), materialDTO.getFechaVenta());
        
        // Verificar que la ciudad existe
        Ciudad ciudad = ciudadRepository.findById(materialDTO.getCiudad().getCodigo())
                .orElseThrow(() -> new CiudadNotFoundException("Ciudad no encontrada con código: " + materialDTO.getCiudad().getCodigo()));
        
        Material material = materialMapper.toEntity(materialDTO);
        material.setCiudad(ciudad);
        
        Material savedMaterial = materialRepository.save(material);
        return materialMapper.toDTO(savedMaterial);
    }
    
    public MaterialDTO update(Long id, MaterialDTO materialDTO) {
        Material existingMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException("Material no encontrado con ID: " + id));
        
        validateDates(materialDTO.getFechaCompra(), materialDTO.getFechaVenta());
        
        // Verificar que la ciudad existe
        Ciudad ciudad = ciudadRepository.findById(materialDTO.getCiudad().getCodigo())
                .orElseThrow(() -> new CiudadNotFoundException("Ciudad no encontrada con código: " + materialDTO.getCiudad().getCodigo()));
        
        // Actualizar campos
        existingMaterial.setNombre(materialDTO.getNombre());
        existingMaterial.setDescripcion(materialDTO.getDescripcion());
        existingMaterial.setTipo(materialDTO.getTipo());
        existingMaterial.setPrecio(materialDTO.getPrecio());
        existingMaterial.setFechaCompra(materialDTO.getFechaCompra());
        existingMaterial.setFechaVenta(materialDTO.getFechaVenta());
        existingMaterial.setEstado(materialDTO.getEstado());
        existingMaterial.setCiudad(ciudad);
        
        Material updatedMaterial = materialRepository.save(existingMaterial);
        return materialMapper.toDTO(updatedMaterial);
    }
    
    public void delete(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new MaterialNotFoundException("Material no encontrado con ID: " + id);
        }
        materialRepository.deleteById(id);
    }
    
    private void validateDates(LocalDate fechaCompra, LocalDate fechaVenta) {
        if (fechaVenta != null && fechaCompra.isAfter(fechaVenta)) {
            throw new InvalidDateException("La fecha de compra no puede ser posterior a la fecha de venta");
        }
    }
}
