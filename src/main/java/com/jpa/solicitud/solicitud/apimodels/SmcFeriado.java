package com.jpa.solicitud.solicitud.apimodels;

public class SmcFeriado {

    private Integer anio;
    private Integer corresponde;
    private Integer acumulado;
    private Integer totalDias;
    private Integer diasTomados;
    private Integer diasPendientes;
    private Integer baseCalculo;
    public Integer getAnio() {
        return anio;
    }
    public void setAnio(Integer anio) {
        this.anio = anio;
    }
    public Integer getCorresponde() {
        return corresponde;
    }
    public void setCorresponde(Integer corresponde) {
        this.corresponde = corresponde;
    }
    public Integer getAcumulado() {
        return acumulado;
    }
    public void setAcumulado(Integer acumulado) {
        this.acumulado = acumulado;
    }
    public Integer getTotalDias() {
        return totalDias;
    }
    public void setTotalDias(Integer totalDias) {
        this.totalDias = totalDias;
    }
    public Integer getDiasTomados() {
        return diasTomados;
    }
    public void setDiasTomados(Integer diasTomados) {
        this.diasTomados = diasTomados;
    }
    public Integer getDiasPendientes() {
        return diasPendientes;
    }
    public void setDiasPendientes(Integer diasPendientes) {
        this.diasPendientes = diasPendientes;
    }
    public Integer getBaseCalculo() {
        return baseCalculo;
    }
    public void setBaseCalculo(Integer baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    

}
