package com.jpa.solicitud.solicitud.models.dto;

import java.time.LocalDate;

public class DecretosResponse {

    private Long id;
    private LocalDate fechaCreacion;
    private Long idSolicitud;
    private Integer rut;
    private String nombre;
    private String tipoSolicitud;
    private LocalDate fechaSolicitud;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double duracion;
    private String depto;
    private String aprobadoPor;
    private String urlPdf;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Long getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    public Integer getRut() {
        return rut;
    }
    public void setRut(Integer rut) {
        this.rut = rut;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipoSolicitud() {
        return tipoSolicitud;
    }
    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }
    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }
    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
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
    public Double getDuracion() {
        return duracion;
    }
    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }
    public String getDepto() {
        return depto;
    }
    public void setDepto(String depto) {
        this.depto = depto;
    }
    public String getAprobadoPor() {
        return aprobadoPor;
    }
    public void setAprobadoPor(String aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }
    public String getUrlPdf() {
        return urlPdf;
    }
    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    




}
