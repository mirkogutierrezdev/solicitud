package com.jpa.solicitud.solicitud.controllers;

import com.jpa.solicitud.solicitud.models.dto.AprobacionDto;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.services.AprobacionService;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprobaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class AprobacionController {

    private final AprobacionService aprobacionService;

    public AprobacionController(AprobacionService aprobacionService) {
        this.aprobacionService = aprobacionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAprobacion(@RequestBody AprobacionDto aprobacionDto) {
        try {
            Aprobacion nuevaAprobacion = aprobacionService.saveAprobacion(aprobacionDto);
            return ResponseEntity.ok(nuevaAprobacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la aprobación: " + e.getMessage());
        }
    }

    @GetMapping("/bySolicitud/{solicitudId}")
    public ResponseEntity<?> getAprobacionBySolicitud(@PathVariable Long solicitudId) {
        try {
            Aprobacion aprobacion = aprobacionService.servGetAprobacionBySolicitud(solicitudId);
            return ResponseEntity.ok(aprobacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al leer la aprobación: " + e.getMessage());
        }
    }

    @PostMapping("/createAprobaciones")
    public ResponseEntity<?> saveAprobaciones(@RequestBody List<AprobacionDto> aprobacionDto) {
        try {
            aprobacionService.saveAprobaciones(aprobacionDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Aprobaciones creadas exitosamente\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear las aprobaciones: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        try {
            List<Aprobacion> aprobacion = aprobacionService.findAll();
            if (aprobacion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la aprobación o el PDF con el ID proporcionado");
            }

            return new ResponseEntity<>(aprobacion, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el PDF: " + e.getMessage());
        }
    }

}
