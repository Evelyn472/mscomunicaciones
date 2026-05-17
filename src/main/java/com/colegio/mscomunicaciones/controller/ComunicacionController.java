package com.colegio.mscomunicaciones.controller;

import com.colegio.mscomunicaciones.dto.ComunicacionRequestDTO;
import com.colegio.mscomunicaciones.dto.ComunicacionResponseDTO;
import com.colegio.mscomunicaciones.service.ComunicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comunicaciones")
@RequiredArgsConstructor
public class ComunicacionController {

    private final ComunicacionService comService;

    @GetMapping
    public List<ComunicacionResponseDTO> obtenerTodas() {
        return comService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunicacionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return comService.obtenerPorId(id) // <-- Aquí ya quedó corregido y amarrado al Service
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/curso/{cursoId}")
    public List<ComunicacionResponseDTO> porCurso(@PathVariable Long cursoId) {
        return comService.obtenerPorCurso(cursoId);
    }

    @GetMapping("/buscar")
    public List<ComunicacionResponseDTO> buscarPorTitulo(@RequestParam String titulo) {
        return comService.buscarPorTitulo(titulo);
    }

    @GetMapping("/estudiante")
    public List<ComunicacionResponseDTO> porEstudiante(@RequestParam("id") Long estudianteId) {
        return comService.buscarPorEstudiante(estudianteId);
    }

    @PostMapping
    public ResponseEntity<ComunicacionResponseDTO> crear(@Valid @RequestBody ComunicacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunicacionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ComunicacionRequestDTO dto) {
        return comService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        comService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}