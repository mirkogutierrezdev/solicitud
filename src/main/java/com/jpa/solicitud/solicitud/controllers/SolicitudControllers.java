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

import com.jpa.solicitud.solicitud.models.dto.SolicitudDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudWithDerivacionesDTO;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;

import com.jpa.solicitud.solicitud.services.SolicitudService;

@RestController
@CrossOrigin(origins = "https://appd.laflorida.cl")
@RequestMapping("/api/solicitud")
public class SolicitudControllers {

    private final SolicitudService solicitudService;

    public SolicitudControllers(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createSolicitud(@RequestBody SolicitudDto solicitudDto) {

        try {
            Solicitud solicitud =  solicitudService.saveSolicitud(solicitudDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(solicitud);
        } catch (IllegalArgumentException e) {
            // Detalles del error
            String errorDetails = String.format("Datos inválidos: %s - %s", e.getMessage(), e.getStackTrace()[0]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + errorDetails + "\"}");
        } catch (Exception e) {
            // Detalles del error
            String errorDetails = String.format("Error al crear la solicitud: %s - %s", e.getMessage(),
                    e.getStackTrace()[0]);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"" + errorDetails + "\"}");
        }
    }

    @GetMapping("/list")
    public List<Solicitud> getSolicitud() {
        return solicitudService.findAll();
    }

    @GetMapping("/departamento/{departamentoId}")
    public List<SolicitudWithDerivacionesDTO> getSolicitudesWithDerivacionesByDepartamento(
            @PathVariable Long departamentoId) {
        return solicitudService.getSolicitudesWithDerivacionesByDepartamento(departamentoId);
    }

    @GetMapping("/byRut/{rut}")
    public ResponseEntity<Object> getSolicitudesByFuncionario(@PathVariable Integer rut) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(solicitudService.servGetSolicitudesPorFuncionario(rut));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al obtener las solicitudes: " + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/entramite/{rut}")
    public ResponseEntity<Object> getPendingAndNotRejectedByFuncionarioRut(@PathVariable Integer rut) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(solicitudService.servGetSolicitudesPendientesPorFuncionario(rut));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al obtener las solicitudes pendientes: " + e.getMessage() + "\"}");
        }
    }

}