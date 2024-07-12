package com.jpa.solicitud.solicitud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.EntradaDto;
import com.jpa.solicitud.solicitud.models.entities.Entrada;
import com.jpa.solicitud.solicitud.services.EntradaService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/entrada")
public class EntradaControllers {

    @Autowired
    private EntradaService entradaService;

    @PostMapping("/create")
    public Entrada createEntrada(@RequestBody EntradaDto entradaDto) {

        return entradaService.saveEntrada(entradaDto);

    }

}
