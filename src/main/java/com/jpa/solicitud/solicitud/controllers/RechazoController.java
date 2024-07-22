package com.jpa.solicitud.solicitud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.RechazoDto;
import com.jpa.solicitud.solicitud.models.entities.Rechazo;
import com.jpa.solicitud.solicitud.services.RechazoService;

@RestController
@RequestMapping("/api/rechazos")
@CrossOrigin(origins = "http://localhost:5173")
public class RechazoController {

    @Autowired
    private RechazoService rechazoService;

    @PostMapping("/create")
	public ResponseEntity<?> createAprobacion(@RequestBody RechazoDto rechazoDto) {
		try {
			Rechazo nuevoRechazo = rechazoService.saveRechazo(rechazoDto);
			return ResponseEntity.ok(nuevoRechazo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al crear la aprobación: " + e.getMessage());
		}
	}


}
