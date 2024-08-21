package com.jpa.solicitud.solicitud.models.dto;

import java.sql.Date;
import java.time.LocalDateTime;

public class SolicitudDto {

    private String tipoSolicitud;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    
    private Integer rut;
    private Long depto;
    private String estado;
    private Date fechaDer;
    private String nombre_departamento;
    private Double duracion;

    
    

    public SolicitudDto() {
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
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

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public void setNombre_departamento(String nombre_departamento) {
        this.nombre_departamento = nombre_departamento;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

  

}
