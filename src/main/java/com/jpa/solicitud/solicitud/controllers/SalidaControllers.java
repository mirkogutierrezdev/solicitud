package com.jpa.solicitud.solicitud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.entities.Salida;
import com.jpa.solicitud.solicitud.services.SalidaService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/salida")
public class SalidaControllers {

    @Autowired
    private SalidaService salidaService;


    @GetMapping("/buscar/{id}")
        public Optional<Salida> getSalidaByI(@PathVariable Long id){
   

        return salidaService.findById(id);
    }

    @GetMapping("/buscar/depto/{depto}")
        public List<Salida> findSalidaByDepto(@PathVariable Long depto){
            return salidaService.findSalidaByDepto(depto);
        }


    }


