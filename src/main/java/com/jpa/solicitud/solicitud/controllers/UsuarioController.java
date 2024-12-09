package com.jpa.solicitud.solicitud.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.models.dto.UsuarioDto;
import com.jpa.solicitud.solicitud.models.entities.Usuario;
import com.jpa.solicitud.solicitud.services.UsuarioService;

@RestController
@CrossOrigin(origins = "https://appx.laflorida.cl")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService=usuarioService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUsuario(@RequestBody UsuarioDto usuarioDto) {
        try {
            Usuario usuario = usuarioService.saveUsuario(usuarioDto);
            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getUsuarios(){
        try {
            List<Usuario> usuario = usuarioService.getUsuarioList();
            return ResponseEntity.ok(usuario);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "+ e.getMessage());
        }
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Object> getUsuario(@PathVariable String rut){
        try {
            Usuario usuario = usuarioService.getusuario(rut);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error "+ e.getMessage());
    }

}
}
