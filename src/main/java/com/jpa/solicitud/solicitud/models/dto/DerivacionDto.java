package com.jpa.solicitud.solicitud.models.dto;

import java.sql.Date;

public class DerivacionDto {

    private Long depto;
    private Long solicitudId;
    private  String estado;
    private  Date fechaDerivacion ;
    private Integer rut;
    private Long derivacionId;
    private String nombreDepartamento;

    
    public Long getDepto() {
        return depto;
    }
    public void setDepto(Long depto) {
        this.depto = depto;
    }
    public Long getSolicitudId() {
        return solicitudId;
    }
    public void setSolicitudId(Long idSolicitud) {
        this.solicitudId = idSolicitud;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFechaDerivacion() {
        return fechaDerivacion;
    }
    public void setFechaDerivacion(Date fechaSolicitud) {
        this.fechaDerivacion = fechaSolicitud;
    }
    public Integer getRut() {
        return rut;
    }
    public void setRut(Integer rut) {
        this.rut = rut;
    }
    public Long getDerivacionId() {
        return derivacionId;
    }
    public void setDerivacionId(Long derivacionId) {
        this.derivacionId = derivacionId;
    }
    public String getNombreDepartamento() {
        return nombreDepartamento;
    }
    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    


    

}
