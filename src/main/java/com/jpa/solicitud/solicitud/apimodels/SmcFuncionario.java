package com.jpa.solicitud.solicitud.apimodels;

import java.util.List;

public class SmcFuncionario extends SmcPersona {

    private String area;
    private SmcDepartamento departamento;
    private SmcContrato contrato;
    private List<SmcAusencia> ausencias;
    private List<SmcFeriados> feriados;
    private List<SmcLicencia> licencias;
    private SmcDiasAdm diasAdm;

    public SmcFuncionario() {
        super();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public SmcDepartamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(SmcDepartamento departamento) {
        this.departamento = departamento;
    }

    public SmcContrato getContrato() {
        return contrato;
    }

    public void setContrato(SmcContrato contrato) {
        this.contrato = contrato;
    }

    public List<SmcAusencia> getAusencias() {
        return ausencias;
    }

    public void setAusencias(List<SmcAusencia> ausencias) {
        this.ausencias = ausencias;
    }

    public List<SmcFeriados> getFeriados() {
        return feriados;
    }

    public void setFeriados(List<SmcFeriados> feriados) {
        this.feriados = feriados;
    }

    public List<SmcLicencia> getLicencias() {
        return licencias;
    }

    public void setLicencias(List<SmcLicencia> licencias) {
        this.licencias = licencias;
    }

    public SmcDiasAdm getDiasAdm() {
        return diasAdm;
    }

    public void setDiasAdm(SmcDiasAdm diasAdm) {
        this.diasAdm = diasAdm;
    }
}
