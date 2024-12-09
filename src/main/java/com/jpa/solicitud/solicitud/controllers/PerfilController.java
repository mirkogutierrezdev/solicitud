package com.jpa.solicitud.solicitud.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.PerfilDto;
import com.jpa.solicitud.solicitud.models.entities.Perfil;
import com.jpa.solicitud.solicitud.services.PerfilService;

@RestController
@CrossOrigin(origins = "https://appx.laflorida.cl")
@RequestMapping("/api/perfiles")
public class PerfilController {

    private final PerfilService perfilService;


    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping("/list")
    public List<Perfil> getPerfiles() {
        return perfilService.getPerfiles();

    }

    @PostMapping("/create")
    public ResponseEntity<Perfil> createPerfil(@RequestBody PerfilDto perfil) {
        Perfil newPerfil = perfilService.savePerfil(perfil);
        return ResponseEntity.ok(newPerfil);
    }

    @PutMapping("/{id}")
public ResponseEntity<Perfil> updatePerfil(@PathVariable Long id, @RequestBody PerfilDto perfilDto) {
    try {
        // Actualiza el perfil con los datos del DTO
        Perfil updatedPerfil = perfilService.updatePerfil(id, perfilDto);
        return ResponseEntity.ok(updatedPerfil);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        try {
            perfilService.deleteById(id); // Llama al servicio para eliminar el perfil
            return ResponseEntity.noContent().build(); // Retorna un código 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna un código 404 si no encuentra el perfil
        }
    }
}
