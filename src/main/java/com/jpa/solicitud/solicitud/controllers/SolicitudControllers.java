package com.jpa.solicitud.solicitud.controllers;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.EntradaDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudDerivacionDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudDto;

import com.jpa.solicitud.solicitud.models.entities.Derivacion;

import com.jpa.solicitud.solicitud.models.entities.Solicitud;

import com.jpa.solicitud.solicitud.services.DerivacionService;
import com.jpa.solicitud.solicitud.services.EntradaService;
import com.jpa.solicitud.solicitud.services.SolicitudService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class SolicitudControllers {

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private DerivacionService derivacionService;

    @Autowired
    private EntradaService entradaService;


    @GetMapping("/calcular")
    public ResponseEntity<Long> calcularDiasHabiles(@RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {
        try {
            long diasHabiles = solicitudService.calcularDiasHabiles(fechaInicio, fechaFin);
            return new ResponseEntity<>(diasHabiles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/solicitud")
    public ResponseEntity<?> createSolicitud(@RequestBody SolicitudDto solicitudDto) {
        
        try {
            solicitudService.procesarSolicitud(solicitudDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Solicitud creada exitosamente\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error al crear la solicitud: " + e.getMessage() + "\"}");
        }
    }
    @GetMapping("/list-solicitudes")
    public List<Solicitud> showSolicitud(){
        return solicitudService.findAll();
    }

    @GetMapping("/solicitudesPorDepartamento/{departamentoCodigo}")
    public ResponseEntity<List<SolicitudDerivacionDto>> obtenerSolicitudesPorDepartamento(@PathVariable Long departamentoCodigo) {
        try {
            List<SolicitudDerivacionDto> solicitudes = solicitudService.obtenerSolicitudesPorDepartamento(departamentoCodigo);
            return new ResponseEntity<>(solicitudes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/solicitudesPorRut/{rut}")
    public ResponseEntity<List<SolicitudDerivacionDto>> obtenerSolicitudesPorRut(@PathVariable Integer rut) {
        try {
            List<SolicitudDerivacionDto> solicitudes = solicitudService.obtenerSolicitudesPorRut(rut);
            return new ResponseEntity<>(solicitudes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/solicitudesPorId/{id}")
    public ResponseEntity<List<Derivacion>> obtenerSolicitudesPorId(@PathVariable Long id) {
        try {
            List<Derivacion> derivaciones = derivacionService.findBySolicitudId(id);
            return new ResponseEntity<>(derivaciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/solnoleidas/{codigoDepto}")
    public ResponseEntity<List<Derivacion>> obtenerSolicitudesNoLeidasPorDepto(@PathVariable Long codigoDepto) {
     
        try {
            List<Derivacion> derivaciones = derivacionService.findNoLeidas(codigoDepto);
            return new ResponseEntity<>(derivaciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/derivacion/{idDerivacion}/{idSolicitud}")
    public ResponseEntity<Void> actualizarLeida(@PathVariable Long idDerivacion, @PathVariable Long idSolicitud,
                                                @RequestParam boolean estado) {
        derivacionService.marcarComoNoLeida(idDerivacion, idSolicitud, estado);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/entrada")
    public ResponseEntity<String> createEntrada(@RequestBody EntradaDto entradaDto) {
        try {
            entradaService.saveEntrada(entradaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Solicitud creada exitosamente\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear la solicitud: " + e.getMessage() + "\"}");
        }
    }

}
