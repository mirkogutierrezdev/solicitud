package com.jpa.solicitud.solicitud.models.dto;

import java.sql.Date;

public class DerivacionDto {

    private Long depto;
    private Long idSolicitud;
    private  String estado;
    private  Date fechaDerivacion ;
    private Integer rut;
    private Long derivacionId;

    
    public Long getDepto() {
        return depto;
    }
    public void setDepto(Long depto) {
        this.depto = depto;
    }
    public Long getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
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


    

}
