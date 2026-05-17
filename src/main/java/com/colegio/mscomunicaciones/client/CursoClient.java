package com.colegio.mscomunicaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cursos", url = "http://localhost:8082/api/curso")
public interface CursoClient {

    @GetMapping("/{id}")
    Object obtenerPorId(@PathVariable("id") Long id);
}