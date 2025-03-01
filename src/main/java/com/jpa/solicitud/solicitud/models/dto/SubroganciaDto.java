package com.jpa.solicitud.solicitud.models.dto;

import java.time.LocalDate;

public class SubroganciaDto {

    private Integer rutJefe;
    private Integer rutSubrogante;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String depto;
    private boolean esDireccion;

    

    

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
    public boolean isEsDireccion() {
        return esDireccion;
    }
    public void setEsDireccion(boolean esDireccion) {
        this.esDireccion = esDireccion;
    }

}
