package com.jpa.solicitud.solicitud.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DecretosResponse {

    private Long id;
    private LocalDateTime fechaCreacion;
    private Long idSolicitud;
    private Integer rut;
    private String nombres;
    private String paterno;
    private String materno;
    private String tipoSolicitud;
    private LocalDate fechaSolicitud;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double duracion;
    private String depto;
    private String aprobadoPor;
    private String tipoContrato;
    private String urlPdf;

    
    public String getPaterno() {
        return paterno;
    }
    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }
    public String getMaterno() {
        return materno;
    }
    public void setMaterno(String materno) {
        this.materno = materno;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
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
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombre) {
        this.nombres = nombre;
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
    public String getTipoContrato() {
        return tipoContrato;
    }
    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    




}
