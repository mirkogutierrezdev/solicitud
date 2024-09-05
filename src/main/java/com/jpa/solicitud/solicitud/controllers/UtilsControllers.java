package com.jpa.solicitud.solicitud.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.apimodels.SmcFeriado;
import com.jpa.solicitud.solicitud.services.UtilsService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/utils")
public class UtilsControllers {

    private final UtilsService utilsService;

    public UtilsControllers(UtilsService utilsService) {
        this.utilsService = utilsService;
    }

    @GetMapping("/calcular")
    public ResponseEntity<Long> getWorkDays(@RequestParam Date fechaInicio,
            @RequestParam Date fechaTermino) {
        try {
            long diasHabiles = utilsService.getWorkDays(fechaInicio, fechaTermino);
            return new ResponseEntity<>(diasHabiles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

}
