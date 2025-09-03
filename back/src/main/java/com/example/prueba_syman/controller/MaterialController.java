package com.example.prueba_syman.controller;

import com.example.prueba_syman.model.dto.ApiResponseDTO;
import com.example.prueba_syman.model.dto.MaterialDTO;
import com.example.prueba_syman.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/materiales")
@CrossOrigin(origins = "*")
public class MaterialController {
    
    @Autowired
    private MaterialService materialService;
    
    /**
     * Buscar todos los materiales
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MaterialDTO>>> getAllMateriales() {
        List<MaterialDTO> materiales = materialService.findAll();
        
        if (materiales.isEmpty()) {
            ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.notFound("No se encontraron materiales");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.success(materiales, 
            "Se encontraron " + materiales.size() + " materiales");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Buscar material por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<MaterialDTO>> getMaterialById(@PathVariable Long id) {
        MaterialDTO material = materialService.findById(id);
        ApiResponseDTO<MaterialDTO> response = ApiResponseDTO.success(material, 
            "Material encontrado exitosamente");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Buscar materiales por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ApiResponseDTO<List<MaterialDTO>>> getMaterialesByTipo(@PathVariable String tipo) {
        List<MaterialDTO> materiales = materialService.findByTipo(tipo);
        
        if (materiales.isEmpty()) {
            ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.notFound(
                "No se encontraron materiales del tipo: " + tipo);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.success(materiales, 
            "Se encontraron " + materiales.size() + " materiales del tipo: " + tipo);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Buscar materiales por fecha de compra
     */
    @GetMapping("/fecha-compra/{fechaCompra}")
    public ResponseEntity<ApiResponseDTO<List<MaterialDTO>>> getMaterialesByFechaCompra(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCompra) {
        
        List<MaterialDTO> materiales = materialService.findByFechaCompra(fechaCompra);
        
        if (materiales.isEmpty()) {
            ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.notFound(
                "No se encontraron materiales con fecha de compra: " + fechaCompra);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.success(materiales, 
            "Se encontraron " + materiales.size() + " materiales con fecha de compra: " + fechaCompra);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Buscar materiales por tipo y fecha de compra
     */
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponseDTO<List<MaterialDTO>>> getMaterialesByTipoAndFechaCompra(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCompra) {
        
        List<MaterialDTO> materiales;
        String mensaje;
        
        if (tipo != null && fechaCompra != null) {
            materiales = materialService.findByTipoAndFechaCompra(tipo, fechaCompra);
            mensaje = "Se encontraron " + materiales.size() + " materiales del tipo '" + tipo + 
                     "' con fecha de compra: " + fechaCompra;
        } else if (tipo != null) {
            materiales = materialService.findByTipo(tipo);
            mensaje = "Se encontraron " + materiales.size() + " materiales del tipo: " + tipo;
        } else if (fechaCompra != null) {
            materiales = materialService.findByFechaCompra(fechaCompra);
            mensaje = "Se encontraron " + materiales.size() + " materiales con fecha de compra: " + fechaCompra;
        } else {
            materiales = materialService.findAll();
            mensaje = "Se encontraron " + materiales.size() + " materiales";
        }
        
        if (materiales.isEmpty()) {
            ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.notFound(
                "No se encontraron materiales con los criterios especificados");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.success(materiales, mensaje);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Buscar materiales por ciudad
     */
    @GetMapping("/ciudad/{ciudadIdentifier}")
    public ResponseEntity<ApiResponseDTO<List<MaterialDTO>>> getMaterialesByCiudad(
            @PathVariable String ciudadIdentifier) {
        
        List<MaterialDTO> materiales = materialService.findByCiudad(ciudadIdentifier);
        
        if (materiales.isEmpty()) {
            ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.notFound(
                "No se encontraron materiales en la ciudad: " + ciudadIdentifier);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        ApiResponseDTO<List<MaterialDTO>> response = ApiResponseDTO.success(materiales, 
            "Se encontraron " + materiales.size() + " materiales en la ciudad: " + ciudadIdentifier);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Crear nuevo material
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<MaterialDTO>> createMaterial(@Valid @RequestBody MaterialDTO materialDTO) {
        MaterialDTO nuevoMaterial = materialService.create(materialDTO);
        ApiResponseDTO<MaterialDTO> response = ApiResponseDTO.success(nuevoMaterial, 
            "Material creado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Actualizar material existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<MaterialDTO>> updateMaterial(
            @PathVariable Long id, @Valid @RequestBody MaterialDTO materialDTO) {
        
        MaterialDTO materialActualizado = materialService.update(id, materialDTO);
        ApiResponseDTO<MaterialDTO> response = ApiResponseDTO.success(materialActualizado, 
            "Material actualizado exitosamente");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Eliminar material
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Object>> deleteMaterial(@PathVariable Long id) {
        materialService.delete(id);
        ApiResponseDTO<Object> response = ApiResponseDTO.success(null, 
            "Material eliminado exitosamente");
        return ResponseEntity.ok(response);
    }
}
