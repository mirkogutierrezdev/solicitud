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

import com.jpa.solicitud.solicitud.models.dto.SolicitudDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudWithDerivacionesDTO;
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
            solicitudService.saveSolicitud(solicitudDto);
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
    public List<Solicitud> getSolicitud() {
        return solicitudService.findAll();
    }


    @GetMapping("/departamento/{departamentoId}")
    public List<SolicitudWithDerivacionesDTO> getSolicitudesWithDerivacionesByDepartamento(@PathVariable Long departamentoId) {
        return solicitudService.getSolicitudesWithDerivacionesByDepartamento(departamentoId);
    }
}