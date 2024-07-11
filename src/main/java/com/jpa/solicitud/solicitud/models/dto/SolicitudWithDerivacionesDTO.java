package com.jpa.solicitud.solicitud.models.dto;



import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Entrada;
import com.jpa.solicitud.solicitud.models.entities.Salida;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;

import java.util.List;

public class SolicitudWithDerivacionesDTO {

    private Solicitud solicitud;
    private List<Derivacion> derivaciones;
    private List<Entrada> entradas;
    private List<Salida> salidas;

    // Getters y setters

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public List<Derivacion> getDerivaciones() {
        return derivaciones;
    }

    public void setDerivaciones(List<Derivacion> derivaciones) {
        this.derivaciones = derivaciones;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public List<Salida> getSalidas() {
        return salidas;
    }

    public void setSalidas(List<Salida> salidas) {
        this.salidas = salidas;
    }
}
