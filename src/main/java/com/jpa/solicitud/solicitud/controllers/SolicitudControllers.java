package com.jpa.solicitud.solicitud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.SolicitudDerivacionDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudDto;

import com.jpa.solicitud.solicitud.models.entities.Solicitud;

import com.jpa.solicitud.solicitud.services.SolicitudService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/solicitud")
public class SolicitudControllers {

    @Autowired
    private SolicitudService solicitudService;

    @PostMapping("/create")
    public ResponseEntity<?> createSolicitud(@RequestBody SolicitudDto solicitudDto) {

        try {
            solicitudService.procesarSolicitud(solicitudDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Solicitud creada exitosamente\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear la solicitud: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/list")
    public List<Solicitud> showSolicitud() {
        return solicitudService.findAll();
    }

    @GetMapping("/byDepto/{depto}")
    public ResponseEntity<List<SolicitudDerivacionDto>> obtenerSolicitudesPorDepartamento(
            @PathVariable Long depto) {
        try {
            List<SolicitudDerivacionDto> solicitudes = solicitudService
                    .obtenerSolicitudesPorDepartamento(depto);
            return new ResponseEntity<>(solicitudes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byRut/{rut}")
    public ResponseEntity<List<SolicitudDerivacionDto>> obtenerSolicitudesPorRut(@PathVariable Integer rut) {
        try {
            List<SolicitudDerivacionDto> solicitudes = solicitudService.obtenerSolicitudesPorRut(rut);
            return new ResponseEntity<>(solicitudes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
