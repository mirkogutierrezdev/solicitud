package com.jpa.solicitud.solicitud.services;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.repositories.ITipoSolicitudRepository;

@Service
public class TipoSolService {

    private final ITipoSolicitudRepository tipoSolicitudRepository;

    public TipoSolService(ITipoSolicitudRepository tipoSolicitudRepository) {
        this.tipoSolicitudRepository = tipoSolicitudRepository;
    }

    public Long getIdByName(String nombre) {

        return tipoSolicitudRepository.findIdByNombre(nombre);
    }
}
