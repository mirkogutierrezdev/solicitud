package com.jpa.solicitud.solicitud.models.dto;

import java.sql.Date;

public class SolicitudDto {

    private String tipoSolicitud;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaSol;
    private Integer rut;
    private Long depto;
    private String estado;
    private Date fechaDer;
    
    public SolicitudDto() {
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaSol() {
        return fechaSol;
    }

    public Date setFechaSol(Date fechaSol) {
        return this.fechaSol = fechaSol;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public Long getDepto() {
        return depto;
    }

    public void setDepto(Long depto) {
        this.depto = depto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaDer() {
        return fechaDer;
    }

    public void setFechaDer(Date fechaDer) {
        this.fechaDer = fechaDer;
    }

    

    
}
