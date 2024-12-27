package com.jpa.solicitud.solicitud.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.SubroganciaDto;
import com.jpa.solicitud.solicitud.models.dto.ViewSubroganciaDto;
import com.jpa.solicitud.solicitud.models.entities.Subrogancia;
import com.jpa.solicitud.solicitud.services.SubroganciaService;

@RestController
@RequestMapping("/api/sub")
@CrossOrigin(origins = "https://appx.laflorida.cl")
public class SubroganciaControllers {

    private final SubroganciaService subroganciaService;

    public SubroganciaControllers(SubroganciaService subroganciaService){
        this.subroganciaService = subroganciaService;
    }


    @PostMapping("/create")
	public ResponseEntity<Object> createRechazo(@RequestBody SubroganciaDto subroganciaDto) {
		try {
			Subrogancia subrogancia = subroganciaService.saveSubrogancia(subroganciaDto);
			return ResponseEntity.ok(subrogancia);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al crear la subrogancia: " + e.getMessage());
		}
	}

	@GetMapping("/by-rut")
    public ResponseEntity<List<Subrogancia>> getSubroganciasByRutSubrogante(@RequestParam Integer rutSubrogante, 
     @RequestParam LocalDate fechaInicio,@RequestParam LocalDate fechaFin) {
        List<Subrogancia> subrogancias = subroganciaService.getSubroganciasByRutSubrogante(rutSubrogante, fechaInicio,fechaFin);
        return ResponseEntity.ok(subrogancias);
    }

	 @GetMapping("/view/by-rut")
    public ResponseEntity<List<ViewSubroganciaDto>> getSubroganciasViewByRutSubrogante(@RequestParam Integer rutSubrogante,
    @RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin) {
        try {
            List<ViewSubroganciaDto> subrogancias = subroganciaService.getSubroganciasByRut(rutSubrogante, fechaInicio,fechaFin);
            return ResponseEntity.ok(subrogancias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
