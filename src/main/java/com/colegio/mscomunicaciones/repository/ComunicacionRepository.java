package com.colegio.mscomunicaciones.repository;

import com.colegio.mscomunicaciones.model.Comunicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; 
import java.util.List;

@Repository 
public interface ComunicacionRepository extends JpaRepository<Comunicacion, Long> {

    List<Comunicacion> findByCursoId(Long cursoId);

    @Query("SELECT c FROM Comunicacion c WHERE LOWER(c.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
    List<Comunicacion> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT c FROM Comunicacion c WHERE c.estudianteId = :estudianteId ORDER BY c.fecha DESC")
    List<Comunicacion> buscarPorEstudianteOrdenado(@Param("estudianteId") Long estudianteId);
}