package com.jpa.solicitud.solicitud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;

import jakarta.transaction.Transactional;

@Service
public class DerivacionService {

    @Autowired
    private IDerivacionRepository derivacionRepository;

    public List<Derivacion> findBySolicitudId(Long id) {
        return derivacionRepository.findBySolicitudId(id);
    }

    public List<Derivacion> findNoLeidas(Long depto) {
        return derivacionRepository.findNoLeidas(depto);
    }

    @Transactional
    public void marcarComoNoLeida(Long idDerivacion, Long idSolicutd, Boolean estado) {
        derivacionRepository.marcarComoNoLeida(idDerivacion, idSolicutd, estado);
    }

}
