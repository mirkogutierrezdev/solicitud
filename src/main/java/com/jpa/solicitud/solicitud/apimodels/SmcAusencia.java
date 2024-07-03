package com.jpa.solicitud.solicitud.apimodels;

import java.sql.Date;

public class SmcAusencia {

    private Integer ident;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_termino;
    private Long dias_ausencia;
    private Integer dias_feriados;
    
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
    public Date getFecha_inicio() {
        return fecha_inicio;
    }
    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
    public Date getFecha_termino() {
        return fecha_termino;
    }
    public void setFecha_termino(Date fecha_termino) {
        this.fecha_termino = fecha_termino;
    }
    public Long getDias_ausencia() {
        return dias_ausencia;
    }
    public void setDias_ausencia(Long dias_ausencia) {
        this.dias_ausencia = dias_ausencia;
    }
    public Integer getDias_feriados() {
        return dias_feriados;
    }
    public void setDias_feriados(Integer dias_feriados) {
        this.dias_feriados = dias_feriados;
    }


    

}
