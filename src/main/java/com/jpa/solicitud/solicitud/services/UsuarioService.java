package com.jpa.solicitud.solicitud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.dto.UsuarioDto;
import com.jpa.solicitud.solicitud.models.entities.Perfil;
import com.jpa.solicitud.solicitud.models.entities.Usuario;
import com.jpa.solicitud.solicitud.repositories.IPerfilRepository;
import com.jpa.solicitud.solicitud.repositories.IUsuarioRepository;

@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    private final IPerfilRepository perfilRepository;

    public UsuarioService(IUsuarioRepository usuarioRepository, IPerfilRepository perfilRepository){
        this.usuarioRepository=usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    public Usuario saveUsuario(UsuarioDto usuarioDto) {
        // Crear una nueva instancia de Usuario
        Usuario usuario = new Usuario();
        usuario.setRut(usuarioDto.getRut());
    
        // Validar y asignar el perfil al usuario
        Long perfilId = usuarioDto.getIdPerfil();
        Optional<Perfil> perfil = perfilRepository.findById(perfilId);
        if (perfil.isPresent()) {
            usuario.setPerfil(perfil.get());
        } else {
            throw new IllegalArgumentException("Perfil no encontrado con ID: " + perfilId);
        }
    
        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }
    


    public List<Usuario> getUsuarioList(){
        return usuarioRepository.findAll();
    }

    public Usuario getusuario(String rut){
        return usuarioRepository.findByRut(rut);
    }

}
