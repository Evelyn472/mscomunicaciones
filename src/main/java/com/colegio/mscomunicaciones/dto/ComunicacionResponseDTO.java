package com.colegio.mscomunicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunicacionResponseDTO {
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDate fecha;
    private Long cursoId;
    private Long estudianteId;
}