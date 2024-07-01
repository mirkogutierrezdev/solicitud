package com.jpa.solicitud.solicitud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.repositories.ITipoSolicitudRepository;

@Service
public class TipoSolService {

    @Autowired
    private ITipoSolicitudRepository tipoSolicitudRepository;


    public Long getIdByName(String nombre){

        return tipoSolicitudRepository.findIdByNombre(nombre);
    }
}
