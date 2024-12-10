package com.jpa.solicitud.solicitud.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.DecretosDto;
import com.jpa.solicitud.solicitud.models.entities.Decretos;
import com.jpa.solicitud.solicitud.services.DecretosService;

@RestController
@RequestMapping("/api/decretos")
@CrossOrigin(origins = "https://appx.laflorida.cl")
public class DecretoController {

    private final DecretosService decretosService;

    public DecretoController(DecretosService decretosService) {
        this.decretosService = decretosService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Object> crearDecreto(@RequestBody DecretosDto decretoDTO) {
        try {
            Decretos nuevoDecreto = decretosService.crearDecreto(decretoDTO);
            return ResponseEntity.ok(nuevoDecreto);
        } catch (Exception e) {
            // Capturamos cualquier excepción y devolvemos un mensaje adecuado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el decreto: " + e.getMessage());
        }
    }

    
}
