package com.example.prueba_syman.repository;

import com.example.prueba_syman.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    
    List<Material> findByTipo(String tipo);
    
    List<Material> findByFechaCompra(LocalDate fechaCompra);
    
    List<Material> findByTipoAndFechaCompra(String tipo, LocalDate fechaCompra);
    
    @Query("SELECT m FROM Material m WHERE m.ciudad.codigo = :codigoCiudad")
    List<Material> findByCiudadCodigo(@Param("codigoCiudad") String codigoCiudad);
    
    @Query("SELECT m FROM Material m WHERE m.ciudad.nombre = :nombreCiudad")
    List<Material> findByCiudadNombre(@Param("nombreCiudad") String nombreCiudad);
}
