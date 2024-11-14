package com.jpa.solicitud.solicitud.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.RechazoDto;
import com.jpa.solicitud.solicitud.models.entities.Rechazo;
import com.jpa.solicitud.solicitud.services.RechazoService;

@RestController
@RequestMapping("/api/rechazos")
@CrossOrigin(origins = "https://appd.laflorida.cl")
public class RechazoController {

	private final RechazoService rechazoService;

	public RechazoController(RechazoService rechazoService) {
		this.rechazoService = rechazoService;
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createRechazo(@RequestBody RechazoDto rechazoDto) {
		try {
			Rechazo nuevoRechazo = rechazoService.saveRechazo(rechazoDto);
			return ResponseEntity.ok(nuevoRechazo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al crear la aprobación: " + e.getMessage());
		}
	}

	@GetMapping("/bySolicitud/{solicitudId}")
	public ResponseEntity<Object> ctrlgetSolicitudById(@PathVariable Long solicitudId) {

		try {
			Rechazo rechazo = rechazoService.servGetRechazoBySolicitud(solicitudId);
			return ResponseEntity.ok(rechazo);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al traer el rechazo" + e.getMessage());
		}
	}

}
