package com.jpa.solicitud.solicitud.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.services.UtilsService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/utils")
public class UtilsControllers {

    @Autowired
    private UtilsService utilsService;

    @GetMapping("/calcular")
    public ResponseEntity<Long> calcularDiasHabiles(@RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {
        try {
            long diasHabiles = utilsService.calcularDiasHabiles(fechaInicio, fechaFin);
            return new ResponseEntity<>(diasHabiles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/next/{depto}")
    public String determinaJefatura(@PathVariable Long depto){
        return utilsService.determinaDerivacion(depto);
    }

    
}
