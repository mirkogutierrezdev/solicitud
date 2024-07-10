package com.jpa.solicitud.solicitud.models.dto;

import java.sql.Date;

public class SolicitudEntradaDto {


    private String nombre;
    private String nombreDepartamento;
    private Date fechaEntrada;
 
    private String comentarios;
    private Long solicitudId;
    private Long funcionarioId;
    private Integer rut;
    private Date fechaSolicitud;
    private Date fechaInicio;
    private Date fechaFin;
    private Long tipoSolicitudId;
    private String nombreSolicitud;
    private Long estadoId;
    private String nombreEstado;

    private Long departamentoCodigo;
   
    private Boolean leida;

    
 
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombreFuncionario) {
        this.nombre = nombreFuncionario;
    }
    public String getNombreDepartamento() {
        return nombreDepartamento;
    }
    public void setNombreDepartamento(String nombreDepto) {
        this.nombreDepartamento = nombreDepto;
    }
    public Date getFechaEntrada() {
        return fechaEntrada;
    }
    public void setFechaEntrada(Date fecheEntrada) {
        this.fechaEntrada = fecheEntrada;
    }
 
 
    public String getComentarios() {
        return comentarios;
    }
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    public Long getSolicitudId() {
        return solicitudId;
    }
    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }
    public Long getFuncionarioId() {
        return funcionarioId;
    }
    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
    public Integer getRut() {
        return rut;
    }
    public void setRut(Integer rut) {
        this.rut = rut;
    }
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
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
    public Long getTipoSolicitudId() {
        return tipoSolicitudId;
    }
    public void setTipoSolicitudId(Long tipoSolicitudId) {
        this.tipoSolicitudId = tipoSolicitudId;
    }
    public String getNombreSolicitud() {
        return nombreSolicitud;
    }
    public void setNombreSolicitud(String nombreSolicitud) {
        this.nombreSolicitud = nombreSolicitud;
    }
    public Long getEstadoId() {
        return estadoId;
    }
    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }
    public String getNombreEstado() {
        return nombreEstado;
    }
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
   Long getDepartamentoCodigo() {
        return departamentoCodigo;
    }
    public void setDepartamentoCodigo(Long departamentoCodigo) {
        this.departamentoCodigo = departamentoCodigo;
    }
    public Boolean getLeida() {
        return leida;
    }
    public void setLeida(Boolean leida) {
        this.leida = leida;
    }
   
    
    



}
