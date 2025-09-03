package com.example.prueba_syman.controller;

import com.example.prueba_syman.model.dto.ApiResponseDTO;
import com.example.prueba_syman.model.dto.CiudadDTO;
import com.example.prueba_syman.model.dto.DepartamentoDTO;
import com.example.prueba_syman.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CiudadController {
    
    @Autowired
    private CiudadService ciudadService;
    
    /**
     * Obtener todas las ciudades
     */
    @GetMapping("/ciudades")
    public ResponseEntity<ApiResponseDTO<List<CiudadDTO>>> getAllCiudades() {
        List<CiudadDTO> ciudades = ciudadService.findAllCiudades();
        ApiResponseDTO<List<CiudadDTO>> response = ApiResponseDTO.success(ciudades, 
            "Se encontraron " + ciudades.size() + " ciudades");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener todos los departamentos
     */
    @GetMapping("/departamentos")
    public ResponseEntity<ApiResponseDTO<List<DepartamentoDTO>>> getAllDepartamentos() {
        List<DepartamentoDTO> departamentos = ciudadService.findAllDepartamentos();
        ApiResponseDTO<List<DepartamentoDTO>> response = ApiResponseDTO.success(departamentos, 
            "Se encontraron " + departamentos.size() + " departamentos");
        return ResponseEntity.ok(response);
    }
}
