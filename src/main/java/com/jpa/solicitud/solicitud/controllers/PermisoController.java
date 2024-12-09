package com.jpa.solicitud.solicitud.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.entities.Permiso;
import com.jpa.solicitud.solicitud.services.PermisoService;

@RestController
@CrossOrigin(origins = "https://appx.laflorida.cl")
@RequestMapping("/api/permisos")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService){
        this.permisoService = permisoService;
    }


     @GetMapping("/list")
    public List<Permiso> getPermiso() {
        return permisoService.getPermisos();

    }




  
}
