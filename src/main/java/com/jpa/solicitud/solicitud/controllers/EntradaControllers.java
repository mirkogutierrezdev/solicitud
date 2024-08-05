package com.jpa.solicitud.solicitud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.EntradaDto;
import com.jpa.solicitud.solicitud.services.EntradaService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/entrada")
public class EntradaControllers {

    @Autowired
    private EntradaService entradaService;

    @PostMapping("/create")
    public ResponseEntity<?> createEntrada(@RequestBody EntradaDto entradaDto) {

        try {
             entradaService.saveEntrada(entradaDto);
             return ResponseEntity.status(HttpStatus.CREATED)
                     .body("{\"message\": \"Entrada creada exitosamente\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("{\"error\": \"Error al crear la entrada: " + e.getMessage() + "\"}");
        }

        

    }

}
