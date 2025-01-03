package com.jpa.solicitud.solicitud.models.dto;

import java.time.LocalDate;

public class SubroganciaDto {

    private Integer rutJefe;
    private Integer rutSubrogante;
    private Long idSolicitud;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String depto;

    

    public Integer getRutJefe() {
        return rutJefe;
    }
    public void setRutJefe(Integer rutJefe) {
        this.rutJefe = rutJefe;
    }
    public Integer getRutSubrogante() {
        return rutSubrogante;
    }
    public void setRutSubrogante(Integer rutSubrogante) {
        this.rutSubrogante = rutSubrogante;
    }
    public Long getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
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
    public String getDepto() {
        return depto;
    }
    public void setDepto(String depto) {
        this.depto = depto;
    }

}
