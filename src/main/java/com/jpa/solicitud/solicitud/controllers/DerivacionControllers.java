package com.jpa.solicitud.solicitud.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.DerivacionDto;
import com.jpa.solicitud.solicitud.models.dto.VDerivacionDto;
import com.jpa.solicitud.solicitud.services.DerivacionService;

@RestController
@CrossOrigin(origins = "http://localhost")
@RequestMapping("/api/derivacion")
public class DerivacionControllers {

    private final DerivacionService derivacionService;

    public DerivacionControllers(DerivacionService derivacionService) {
        this.derivacionService = derivacionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDerivacion(@RequestBody DerivacionDto DerivacionDto) {

        try {
            derivacionService.saveDerivacion(DerivacionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Solicitud creada exitosamente\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear la solicitud: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/solicitud/{id}")
    public ResponseEntity<?> getDerivacionesBySolicitud(@PathVariable Long id) {
        try {
            List<VDerivacionDto> derivaciones = derivacionService.findDerivacionesBySolicitud(id);
            return ResponseEntity.status(HttpStatus.OK).body(derivaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al obtener las derivaciones: " + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/createDerivaciones")
    public ResponseEntity<?> saveDerivaciones(@RequestBody List<DerivacionDto> derivacionesDto) {
        try {
            derivacionService.saveDerivaciones(derivacionesDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Derivaciones creadas exitosamente\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear las derivaciones: " + e.getMessage() + "\"}");
        }
    }

}
