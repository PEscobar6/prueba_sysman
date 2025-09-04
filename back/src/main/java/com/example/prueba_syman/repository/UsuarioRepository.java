package com.example.prueba_syman.repository;

import com.example.prueba_syman.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByUsernameOrEmail(String username, String email);
    
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);
    
    @Modifying
    @Query("UPDATE Usuario u SET u.ultimoAcceso = :fechaAcceso WHERE u.username = :username")
    void updateUltimoAcceso(@Param("username") String username, @Param("fechaAcceso") LocalDateTime fechaAcceso);
    
    @Query("SELECT u FROM Usuario u WHERE u.estado = 'ACTIVO'")
    java.util.List<Usuario> findActiveUsers();
}
