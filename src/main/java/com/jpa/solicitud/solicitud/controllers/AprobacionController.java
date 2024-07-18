package com.jpa.solicitud.solicitud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jpa.solicitud.solicitud.models.dto.AprobacionDto;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.services.AprobacionService;

@RestController
@RequestMapping("/api/aprobaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class AprobacionController {

	@Autowired
	private AprobacionService aprobacionService;

	@PostMapping("/create")
	public ResponseEntity<?> createAprobacion(@RequestBody AprobacionDto aprobacionDto) {
		try {
			Aprobacion nuevaAprobacion = aprobacionService.saveAprobacion(aprobacionDto);
			return ResponseEntity.ok(nuevaAprobacion);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al crear la aprobación: " + e.getMessage());
		}
	}

}
