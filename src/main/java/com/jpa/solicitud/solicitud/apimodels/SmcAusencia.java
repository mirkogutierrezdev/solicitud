package com.jpa.solicitud.solicitud.apimodels;

import java.sql.Date;

public class SmcAusencia {

    private Integer ident;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaTermino;
    private Long diasAusencia;
    private Integer diasFeriados;

    public Integer getIdent() {
        return ident;
    }

    public void setIdent(Integer ident) {
        this.ident = ident;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Long getDiasAusencia() {
        return diasAusencia;
    }

    public void setDiasAusencia(Long diasAusencia) {
        this.diasAusencia = diasAusencia;
    }

    public Integer getDiasFeriados() {
        return diasFeriados;
    }

    public void setDiasFeriados(Integer diasFeriados) {
        this.diasFeriados = diasFeriados;
    }
}
