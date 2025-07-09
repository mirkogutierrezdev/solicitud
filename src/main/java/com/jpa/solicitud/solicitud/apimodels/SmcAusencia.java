package com.jpa.solicitud.solicitud.apimodels;

import java.time.LocalDate;

public class SmcAusencia {

    private Integer ident;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(LocalDate fechaTermino) {
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
