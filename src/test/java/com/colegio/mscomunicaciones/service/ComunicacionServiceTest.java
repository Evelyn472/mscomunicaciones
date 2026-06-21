package com.colegio.mscomunicaciones.service;

import com.colegio.mscomunicaciones.client.CursoClient;
import com.colegio.mscomunicaciones.client.EstudianteClient;
import com.colegio.mscomunicaciones.dto.ComunicacionRequestDTO;
import com.colegio.mscomunicaciones.dto.ComunicacionResponseDTO;
import com.colegio.mscomunicaciones.model.Comunicacion;
import com.colegio.mscomunicaciones.repository.ComunicacionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComunicacionServiceTest {

    @Mock
    private ComunicacionRepository comRepository;

    @Mock
    private CursoClient cursoClient;

    @Mock
    private EstudianteClient estudianteClient;

    @InjectMocks
    private ComunicacionService comunicacionService;

    @Test
    void guardarComunicacionCorrectamente() {

        ComunicacionRequestDTO dto = new ComunicacionRequestDTO(
                "Reunión de Apoderados",
                "Se cita a reunión el viernes",
                LocalDate.now(),
                1L,
                null
        );

        Comunicacion comunicacionGuardada = new Comunicacion(
                1L,
                "Reunión de Apoderados",
                "Se cita a reunión el viernes",
                LocalDate.now(),
                1L,
                null
        );

        when(cursoClient.obtenerPorId(1L))
                .thenReturn(new Object());

        when(comRepository.save(any(Comunicacion.class)))
                .thenReturn(comunicacionGuardada);

        ComunicacionResponseDTO resultado =
                comunicacionService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Reunión de Apoderados", resultado.getTitulo());

        verify(comRepository, times(1))
                .save(any(Comunicacion.class));
    }

    @Test
    void obtenerComunicacionPorId() {

        Comunicacion comunicacion = new Comunicacion(
                1L,
                "Aviso",
                "Suspensión de clases",
                LocalDate.now(),
                1L,
                null
        );

        when(comRepository.findById(1L))
                .thenReturn(Optional.of(comunicacion));

        Optional<ComunicacionResponseDTO> resultado =
                comunicacionService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());

        verify(comRepository, times(1))
                .findById(1L);
    }

    @Test
    void eliminarComunicacion() {

        doNothing().when(comRepository)
                .deleteById(1L);

        comunicacionService.eliminar(1L);

        verify(comRepository, times(1))
                .deleteById(1L);
    }
}