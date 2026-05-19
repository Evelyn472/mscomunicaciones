package com.colegio.mscomunicaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cursos", url = "http://localhost:8082")
public interface CursoClient {

    @GetMapping("/api/v1/cursos/{id}") 
    Object obtenerPorId(@PathVariable("id") Long id);
}