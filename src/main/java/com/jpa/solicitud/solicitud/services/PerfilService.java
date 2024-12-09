package com.jpa.solicitud.solicitud.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.dto.PerfilDto;
import com.jpa.solicitud.solicitud.models.entities.Perfil;
import com.jpa.solicitud.solicitud.models.entities.Permiso;
import com.jpa.solicitud.solicitud.repositories.IPerfilRepository;
import com.jpa.solicitud.solicitud.repositories.IPermisoRepository;

@Service
public class PerfilService {

    private final IPerfilRepository perfilRepository;

    private final IPermisoRepository permisoRepository;

    public PerfilService(IPerfilRepository perfilRepository, IPermisoRepository permisoRepository) {
        this.perfilRepository = perfilRepository;
        this.permisoRepository = permisoRepository;
    }

    public List<Perfil> getPerfiles() {
        return perfilRepository.findAll();
    }

    public Perfil savePerfil(PerfilDto perfilDto) {
        // Crear el perfil
        Perfil perfil = new Perfil();
        perfil.setNombre(perfilDto.getNombre());
        perfil.setDescripcion(perfilDto.getDescripcion());

        // Asociar los permisos
        Set<Permiso> permisos = new HashSet<>();
        for (Long permisoId : perfilDto.getIdPermisos()) {
            Optional<Permiso> permiso = permisoRepository.findById(permisoId);
            permiso.ifPresent(permisos::add);
        }
        perfil.setPermisos(permisos);

        // Guardar el perfil en la base de datos
        return perfilRepository.save(perfil);
    }

    public Perfil updatePerfil(Long id, PerfilDto perfilDto) {
        // Buscar el perfil existente
        Optional<Perfil> optionalPerfil = perfilRepository.findById(id);
        if (!optionalPerfil.isPresent()) {
            throw new IllegalArgumentException("Perfil no encontrado con ID: " + id);
        }
    
        Perfil perfil = optionalPerfil.get();
        // Actualizar los datos del perfil
        perfil.setNombre(perfilDto.getNombre());
        perfil.setDescripcion(perfilDto.getDescripcion());
    
        // Actualizar los permisos asociados
        Set<Permiso> permisos = new HashSet<>();
        for (Long permisoId : perfilDto.getIdPermisos()) {
            Optional<Permiso> permiso = permisoRepository.findById(permisoId);
            permiso.ifPresent(permisos::add);
        }
        perfil.setPermisos(permisos);
    
        // Guardar y devolver el perfil actualizado
        return perfilRepository.save(perfil);
    }

    public void deleteById(Long id) {
        if (perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Perfil no encontrado");
        }
    }

    
}
