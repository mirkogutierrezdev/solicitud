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

import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.services.DepartamentosService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/departamentos")
public class DepartamentosController {

    @Autowired
    private DepartamentosService departamentosService;

    @PostMapping("/create")
    public ResponseEntity<?> saveAllDepartamentos(@RequestBody List<Departamentos> departamentos) {
        try {
            departamentosService.saveAllDepartamentos(departamentos);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("{\"message\": \"Departamentos creados exitosamente\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error al crear los departamentos: " + e.getMessage() + "\"}");
        }

    }

    @GetMapping("/esjefe/{depto}/{rut}")
    public boolean existsByDeptoIntAndRutJefe(@PathVariable Long depto, @PathVariable Integer rut){

        return departamentosService.existsByDeptoIntAndRutJefe(depto, rut);
    }

    @GetMapping("/buscar/{depto}")
    public Departamentos findByDepto(@PathVariable Long depto){
        return departamentosService.findByDepto(depto);
    }

}
