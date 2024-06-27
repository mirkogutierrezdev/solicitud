package com.jpa.solicitud.solicitud.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.entities.Persona;
import com.jpa.solicitud.solicitud.services.PersonaService;
import com.jpa.solicitud.solicitud.services.SolicitudService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class PersonaControllers {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping("/buscar/{rut}")
    public Persona showPersona(@PathVariable Integer rut) {
        return personaService.findByRut(rut);
    }

    @PostMapping("/guardar/persona")
    public Persona guardarPersona(@RequestBody Persona persona) {
        return personaService.guardaPersona(persona);
    }

    @GetMapping("/calcular")
    public long calcularDiasHabiles(@RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {
        return solicitudService.calcularDiasHabiles(fechaInicio, fechaFin);
    }
}
