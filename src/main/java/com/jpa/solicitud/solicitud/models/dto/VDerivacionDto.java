package com.jpa.solicitud.solicitud.models.dto;

import java.util.Date;

public class VDerivacionDto {
    private Long derivacionId;
    private Date fechaDerivacion;
    private String nombreFuncionarioOrigen;
    private String nombreDepartamentoOrigen;
    private Date fechaEntrada;
    private String nombreFuncionarioEntrada;
    private Date fechaSalida;
    private String nombreFuncionarioSalida;

    

    public VDerivacionDto(Long derivacionId, Date fechaDerivacion, String nombreFuncionarioOrigen,
            String nombreDepartamentoOrigen, Date fechaEntrada, String nombreFuncionarioEntrada, Date fechaSalida,
            String nombreFuncionarioSalida) {
        this.derivacionId = derivacionId;
        this.fechaDerivacion = fechaDerivacion;
        this.nombreFuncionarioOrigen = nombreFuncionarioOrigen;
        this.nombreDepartamentoOrigen = nombreDepartamentoOrigen;
        this.fechaEntrada = fechaEntrada;
        this.nombreFuncionarioEntrada = nombreFuncionarioEntrada;
        this.fechaSalida = fechaSalida;
        this.nombreFuncionarioSalida = nombreFuncionarioSalida;
    }

    public Long getDerivacionId() {
        return derivacionId;
    }

    public void setDerivacionId(Long derivacionId) {
        this.derivacionId = derivacionId;
    }

    public Date getFechaDerivacion() {
        return fechaDerivacion;
    }

    public void setFechaDerivacion(Date fechaDerivacion) {
        this.fechaDerivacion = fechaDerivacion;
    }

    public String getNombreFuncionarioOrigen() {
        return nombreFuncionarioOrigen;
    }

    public void setNombreFuncionarioOrigen(String nombreFuncionarioOrigen) {
        this.nombreFuncionarioOrigen = nombreFuncionarioOrigen;
    }

    public String getNombreDepartamentoOrigen() {
        return nombreDepartamentoOrigen;
    }

    public void setNombreDepartamentoOrigen(String nombreDepartamentoOrigen) {
        this.nombreDepartamentoOrigen = nombreDepartamentoOrigen;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getNombreFuncionarioEntrada() {
        return nombreFuncionarioEntrada;
    }

    public void setNombreFuncionarioEntrada(String nombreFuncionarioEntrada) {
        this.nombreFuncionarioEntrada = nombreFuncionarioEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getNombreFuncionarioSalida() {
        return nombreFuncionarioSalida;
    }

    public void setNombreFuncionarioSalida(String nombreFuncionarioSalida) {
        this.nombreFuncionarioSalida = nombreFuncionarioSalida;
    }

    

}