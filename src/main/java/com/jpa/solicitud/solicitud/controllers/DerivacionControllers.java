package com.jpa.solicitud.solicitud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.entities.Derivacion;
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
}
