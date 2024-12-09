package com.jpa.solicitud.solicitud.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.entities.Permiso;
import com.jpa.solicitud.solicitud.repositories.IPermisoRepository;

@Service
public class PermisoService {

    private final IPermisoRepository permisoRepository;

    public PermisoService(IPermisoRepository permisoRepository){
        this.permisoRepository = permisoRepository;
    }


    public List<Permiso> getPermisos(){

    return permisoRepository.findAll();
    }

}
