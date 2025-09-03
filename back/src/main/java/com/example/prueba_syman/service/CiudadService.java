package com.example.prueba_syman.service;

import com.example.prueba_syman.entities.Ciudad;
import com.example.prueba_syman.entities.Departamento;
import com.example.prueba_syman.model.dto.CiudadDTO;
import com.example.prueba_syman.model.dto.DepartamentoDTO;
import com.example.prueba_syman.repository.CiudadRepository;
import com.example.prueba_syman.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CiudadService {
    
    @Autowired
    private CiudadRepository ciudadRepository;
    
    @Autowired
    private DepartamentoRepository departamentoRepository;
    
    public List<CiudadDTO> findAllCiudades() {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        return ciudades.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<DepartamentoDTO> findAllDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        return departamentos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    private CiudadDTO toDTO(Ciudad ciudad) {
        DepartamentoDTO departamentoDTO = new DepartamentoDTO(
            ciudad.getDepartamento().getCodigo(),
            ciudad.getDepartamento().getNombre()
        );
        
        return new CiudadDTO(
            ciudad.getCodigo(),
            ciudad.getNombre(),
            departamentoDTO
        );
    }
    
    private DepartamentoDTO toDTO(Departamento departamento) {
        return new DepartamentoDTO(
            departamento.getCodigo(),
            departamento.getNombre()
        );
    }
}
