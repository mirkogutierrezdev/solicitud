package com.jpa.solicitud.solicitud.models.dto;

import java.time.LocalDate;

public class ViewSubroganciaDto {
    private Long id;
    private String nombreJefe;
    private String nombreSubrogante;
    private Long depto;
    private String nombreDepto;
    private String estadoSolicitud;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long subDepartamento;

    // Constructor


    public Long getId() {
        return id;
    }

  

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreJefe() {
        return nombreJefe;
    }

    public void setNombreJefe(String nombreJefe) {
        this.nombreJefe = nombreJefe;
    }

    public String getNombreSubrogante() {
        return nombreSubrogante;
    }

    public void setNombreSubrogante(String nombreSubrogante) {
        this.nombreSubrogante = nombreSubrogante;
    }

    public Long getDepto() {
        return depto;
    }

    public void setDepto(Long depto) {
        this.depto = depto;
    }

    public String getNombreDepto() {
        return nombreDepto;
    }

    public void setNombreDepto(String nombreDepto) {
        this.nombreDepto = nombreDepto;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getSubDepartamento() {
        return subDepartamento;
    }

    public void setSubDepartamento(Long subDepartamento) {
        this.subDepartamento = subDepartamento;
    }



    
}
