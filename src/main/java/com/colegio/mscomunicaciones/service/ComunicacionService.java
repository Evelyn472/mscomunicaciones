package com.colegio.mscomunicaciones.service;

import com.colegio.mscomunicaciones.client.CursoClient;
import com.colegio.mscomunicaciones.client.EstudianteClient;
import com.colegio.mscomunicaciones.dto.ComunicacionRequestDTO;
import com.colegio.mscomunicaciones.dto.ComunicacionResponseDTO;
import com.colegio.mscomunicaciones.model.Comunicacion;
import com.colegio.mscomunicaciones.repository.ComunicacionRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComunicacionService {

    private final ComunicacionRepository comRepository;

    private final CursoClient cursoClient;
    private final EstudianteClient estudianteClient;

    private ComunicacionResponseDTO mapToDTO(Comunicacion c) {
        return new ComunicacionResponseDTO(
                c.getId(), c.getTitulo(), c.getMensaje(),
                c.getFecha(), c.getCursoId(), c.getEstudianteId());
    }

    private void validarComponentesComunicacion(Long cursoId, Long estudianteId) {
        
        if (cursoId != null) {
            try {
                cursoClient.obtenerPorId(cursoId);
                log.info(">>> Curso {} validado correctamente (FeignClient)", cursoId);
            } catch (FeignException.NotFound e) {
                throw new RuntimeException(
                        "El curso con id " + cursoId + " no existe en ms-cursos.");
            } catch (FeignException e) {
                throw new RuntimeException(
                        "No se puede conectar con ms-cursos: " + e.getMessage());
            }
        }

        
        if (estudianteId != null) {
            try {
                estudianteClient.obtenerPorId(estudianteId);
                log.info(">>> Estudiante {} validado correctamente (FeignClient)", estudianteId);
            } catch (FeignException.NotFound e) {
                throw new RuntimeException(
                        "El estudiante con id " + estudianteId + " no existe en ms-estudiantes.");
            } catch (FeignException e) {
                throw new RuntimeException(
                        "No se puede conectar con ms-estudiantes: " + e.getMessage());
            }
        }
        
        
        if (cursoId == null && estudianteId == null) {
            throw new RuntimeException("La comunicación debe ir dirigida a un curso o a un estudiante.");
        }
    }

    
    public List<ComunicacionResponseDTO> obtenerTodas() {
        return comRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<ComunicacionResponseDTO> obtenerPorId(Long id) {
        return comRepository.findById(id).map(this::mapToDTO);
    }

    public List<ComunicacionResponseDTO> obtenerPorCurso(Long cursoId) {
        return comRepository.findByCursoId(cursoId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ComunicacionResponseDTO> buscarPorTitulo(String titulo) {
        return comRepository.buscarPorTitulo(titulo)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ComunicacionResponseDTO> buscarPorEstudiante(Long estudianteId) {
        return comRepository.buscarPorEstudianteOrdenado(estudianteId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    public ComunicacionResponseDTO guardar(ComunicacionRequestDTO dto) {
        validarComponentesComunicacion(dto.getCursoId(), dto.getEstudianteId());
        
        LocalDate fechaEmision = (dto.getFecha() != null) ? dto.getFecha() : LocalDate.now();
        
        Comunicacion c = new Comunicacion(null, dto.getTitulo(), dto.getMensaje(),
                fechaEmision, dto.getCursoId(), dto.getEstudianteId());
        return mapToDTO(comRepository.save(c));
    }

    public Optional<ComunicacionResponseDTO> actualizar(Long id, ComunicacionRequestDTO dto) {
        return comRepository.findById(id).map(existente -> {
            validarComponentesComunicacion(dto.getCursoId(), dto.getEstudianteId());
            existente.setTitulo(dto.getTitulo());
            existente.setMensaje(dto.getMensaje());
            if (dto.getFecha() != null) {
                existente.setFecha(dto.getFecha());
            }
            existente.setCursoId(dto.getCursoId());
            existente.setEstudianteId(dto.getEstudianteId());
            return mapToDTO(comRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        comRepository.deleteById(id);
    }
}