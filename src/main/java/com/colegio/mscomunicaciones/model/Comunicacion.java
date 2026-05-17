package com.colegio.mscomunicaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comunicaciones")
public class Comunicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String mensaje;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "curso_id")
    private Long cursoId;

    @Column(name = "estudiante_id")
    private Long estudianteId;
}