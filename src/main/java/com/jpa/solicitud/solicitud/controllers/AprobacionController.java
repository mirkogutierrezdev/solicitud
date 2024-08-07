package com.jpa.solicitud.solicitud.controllers;

import com.jpa.solicitud.solicitud.models.dto.AprobacionDto;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.services.AprobacionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprobaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class AprobacionController {

    @Autowired
    private AprobacionService aprobacionService;

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

    @GetMapping("/pdf/{id}")
    public ResponseEntity<?> getPdf(@PathVariable Long id) {
        try {
            Aprobacion aprobacion = aprobacionService.servGetAprobacionBySolicitud(id);
            if (aprobacion == null ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la aprobación o el PDF con el ID proporcionado");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "document.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(aprobacion.getPdf(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el PDF: " + e.getMessage());
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
}
