package com.jpa.solicitud.solicitud.services;



import java.sql.Date;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.entities.Solicitud;

@Service
public class SolicitudService {

    public long calcularDiasHabiles(Date fechaInicio, Date fechaFin) {
        Solicitud solicitud = new Solicitud();
        return solicitud.calcularDiasHabiles(fechaInicio, fechaFin);
    }
}
