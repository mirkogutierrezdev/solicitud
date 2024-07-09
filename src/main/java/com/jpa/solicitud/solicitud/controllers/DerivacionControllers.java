package com.jpa.solicitud.solicitud.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.apimodels.SmcDepartamento;
import com.jpa.solicitud.solicitud.models.dto.DerivacionDto;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.services.DerivacionService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/derivacion")
public class DerivacionControllers {

    @Autowired
    private DerivacionService derivacionService;

    @PostMapping("/read/{idDerivacion}/{idSolicitud}")
    public ResponseEntity<Void> actualizarLeida(@PathVariable Long idDerivacion, @PathVariable Long idSolicitud,
            @RequestParam boolean estado) {
        derivacionService.marcarComoNoLeida(idDerivacion, idSolicitud, estado);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unreadbydepto/{codigoDepto}")
    public ResponseEntity<List<Derivacion>> obtenerSolicitudesNoLeidasPorDepto(@PathVariable Long codigoDepto) {

        try {
            List<Derivacion> derivaciones = derivacionService.findNoLeidas(codigoDepto);
            return new ResponseEntity<>(derivaciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<List<Derivacion>> obtenerSolicitudesPorId(@PathVariable Long id) {
        try {
            List<Derivacion> derivaciones = derivacionService.findBySolicitudId(id);
            return new ResponseEntity<>(derivaciones, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


  @PostMapping("/create")
    public ResponseEntity<Object> showPrueba(@RequestBody DerivacionDto derivacionDto) {
        try {
            Derivacion derivacion = derivacionService.saveDerivacion(derivacionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(derivacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Datos inválidos: " + e.getMessage() + "\"}");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"No encontrado: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Imprimir el stack trace completo para mayor detalle
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error al crear la derivación: " + e.getMessage() + "\"}");
        }
    }
   
}
