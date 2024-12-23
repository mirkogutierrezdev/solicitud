package com.jpa.solicitud.solicitud.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.apimodels.SmcFeriado;
import com.jpa.solicitud.solicitud.services.UtilsService;

@RestController
@CrossOrigin(origins = "https://appx.laflorida.cl")
@RequestMapping("/api/utils")
public class UtilsControllers {

    private final UtilsService utilsService;

    public UtilsControllers(UtilsService utilsService) {
        this.utilsService = utilsService;
    }

    @GetMapping("/calcular")
public ResponseEntity<Object> getWorkDays(@RequestParam Date fechaInicio,
                                     @RequestParam Date fechaTermino) {

    try {
        long diasHabiles = utilsService.getWorkDays(fechaInicio, fechaTermino);
        return ResponseEntity.ok(diasHabiles);
    } catch (IllegalArgumentException e) {
        // Manejo de errores de entrada inválida
        return ResponseEntity.badRequest().body(Map.of(
            "error", "Parámetros inválidos",
            "mensaje", e.getMessage()
        ));
    } catch (Exception e) {
        // Manejo genérico para errores inesperados
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "error", "Error interno del servidor",
            "mensaje", e.getMessage() // Puedes registrar detalles del error
        ));
    }
}


    @GetMapping("/feriados/obtener")
    public ResponseEntity<List<SmcFeriado>> getFeriados(@RequestParam Date fechaInicio,
            @RequestParam Date fechaTermino) {
        try {
            List<SmcFeriado> feriados = utilsService.getFeriados(fechaInicio, fechaTermino);
            return new ResponseEntity<>(feriados, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/jefe/{depto}")
    public ResponseEntity<Object> getNombreJefeSupervidor(@PathVariable Long depto){
        try {
            String nombre= utilsService.jefeDestino(depto);
            return ResponseEntity.ok(nombre);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
