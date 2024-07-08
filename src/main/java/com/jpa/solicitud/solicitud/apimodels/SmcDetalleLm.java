package com.jpa.solicitud.solicitud.apimodels;

import java.sql.Date;

public class SmcDetalleLm {

    private Date fechaInicio;
    private Date fechaFin;
    private Integer diasPago;
    private Long imponiblePromedio;
    private Long imposicionesPromedio;
    private Long saludPromedio;
    private Long liquidoPromedio;
    private Long subLiquido;
    private Long subImposiciones;
    private Long subSalud;
    private Long numlic;

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

    public Integer getDiasPago() {
        return diasPago;
    }

    public void setDiasPago(Integer diasPago) {
        this.diasPago = diasPago;
    }

    public Long getImponiblePromedio() {
        return imponiblePromedio;
    }

    public void setImponiblePromedio(Long imponiblePromedio) {
        this.imponiblePromedio = imponiblePromedio;
    }

    public Long getImposicionesPromedio() {
        return imposicionesPromedio;
    }

    public void setImposicionesPromedio(Long imposicionesPromedio) {
        this.imposicionesPromedio = imposicionesPromedio;
    }

    public Long getSaludPromedio() {
        return saludPromedio;
    }

    public void setSaludPromedio(Long saludPromedio) {
        this.saludPromedio = saludPromedio;
    }

    public Long getLiquidoPromedio() {
        return liquidoPromedio;
    }

    public void setLiquidoPromedio(Long liquidoPromedio) {
        this.liquidoPromedio = liquidoPromedio;
    }

    public Long getSubLiquido() {
        return subLiquido;
    }

    public void setSubLiquido(Long subLiquido) {
        this.subLiquido = subLiquido;
    }

    public Long getSubImposiciones() {
        return subImposiciones;
    }

    public void setSubImposiciones(Long subImposiciones) {
        this.subImposiciones = subImposiciones;
    }

    public Long getSubSalud() {
        return subSalud;
    }

    public void setSubSalud(Long subSalud) {
        this.subSalud = subSalud;
    }

    public Long getNumlic() {
        return numlic;
    }

    public void setNumlic(Long numlic) {
        this.numlic = numlic;
    }
}
