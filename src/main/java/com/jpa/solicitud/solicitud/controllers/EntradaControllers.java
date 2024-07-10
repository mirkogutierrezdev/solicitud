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

import com.jpa.solicitud.solicitud.models.dto.EntradaDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudEntradaDto;
import com.jpa.solicitud.solicitud.models.entities.Entrada;
import com.jpa.solicitud.solicitud.services.EntradaService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/entrada")
public class EntradaControllers {

    
    @Autowired
    private EntradaService entradaService;
        
    @GetMapping("/get/{id}")
    public ResponseEntity<Entrada> entradaById(@PathVariable Long id){


        try {
            Entrada entrada = entradaService.findById(id);
            return new ResponseEntity<>(entrada,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

       @GetMapping("/list")
    public ResponseEntity<List<Entrada>> listEntradas(){


        try {
            List<Entrada> entrada = entradaService.findAll();
            return new ResponseEntity<>(entrada,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     @PostMapping("/create")
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

    @GetMapping("buscarDepto/{depto}")
    public ResponseEntity<List<SolicitudEntradaDto>> getEntradaByDepto(@PathVariable Long depto){
        try {
            List<SolicitudEntradaDto> entrada = entradaService.findEntradaByDepto(depto);
            return new ResponseEntity<>(entrada,HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
}
