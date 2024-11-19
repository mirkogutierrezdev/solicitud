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
@CrossOrigin(origins = "https://appx.laflorida.cl")

public class AprobacionController {

    private final AprobacionService aprobacionService;

    public AprobacionController(AprobacionService aprobacionService) {
        this.aprobacionService = aprobacionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAprobacion(@RequestBody AprobacionDto aprobacionDto) {
        try {
            Aprobacion nuevaAprobacion = aprobacionService.saveAprobacion(aprobacionDto);
            return ResponseEntity.ok(nuevaAprobacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la aprobación: " + e.getMessage());
        }
    }

    @GetMapping("/bySolicitud/{solicitudId}")
    public ResponseEntity<Object> getAprobacionBySolicitud(@PathVariable Long solicitudId) {
        try {
            Aprobacion aprobacion = aprobacionService.servGetAprobacionBySolicitud(solicitudId);
            return ResponseEntity.ok(aprobacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al leer la aprobación: " + e.getMessage());
        }
    }

    @PostMapping("/createAprobaciones")
    public ResponseEntity<Object> saveAprobaciones(@RequestBody List<AprobacionDto> aprobacionDto) {
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
    public ResponseEntity<Object> getAll() {
        try {
            List<Aprobacion> aprobacion = aprobacionService.getAllWithoutDecreto();
            if (aprobacion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron aprobaciones");
            }

            return new ResponseEntity<>(aprobacion, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener  el listado de aprobaciones: " + e.getMessage());
        }
    }


    @GetMapping("/getaprobaciones")
    public ResponseEntity<Object> getAprobaciones() {
        try {
            List<Aprobacion> aprobacion = aprobacionService.getAllWithoutDecreto();
            if (aprobacion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron aprobaciones");
            }

            return new ResponseEntity<>(aprobacion, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de aprobaciones: " + e.getMessage());
        }
    }

}
