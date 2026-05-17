package com.colegio.mscomunicaciones.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunicacionRequestDTO {

    @NotBlank(message = "El título del comunicado no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El contenido del mensaje es obligatorio")
    private String mensaje;

    private LocalDate fecha;

    private Long cursoId;

    private Long estudianteId;
}