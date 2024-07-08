package com.jpa.solicitud.solicitud.apimodels;

import java.sql.Date;

public class SmcLicencia {

    private Long numlic;
    private Date fechaInicio;
    private String prevision;
    private String afp;
    private Integer diasLic;
    private Date fechaEmision;
    private Date fechaRecepcion;
    private String rutMedico;
    private String nombreProfesional;
    private String tipoLicencia;
    private SmcDetalleLm detalleLM;

    public Long getNumlic() {
        return numlic;
    }

    public void setNumlic(Long numlic) {
        this.numlic = numlic;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getPrevision() {
        return prevision;
    }

    public void setPrevision(String prevision) {
        this.prevision = prevision;
    }

    public String getAfp() {
        return afp;
    }

    public void setAfp(String afp) {
        this.afp = afp;
    }

    public Integer getDiasLic() {
        return diasLic;
    }

    public void setDiasLic(Integer diasLic) {
        this.diasLic = diasLic;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getRutMedico() {
        return rutMedico;
    }

    public void setRutMedico(String rutMedico) {
        this.rutMedico = rutMedico;
    }

    public String getNombreProfesional() {
        return nombreProfesional;
    }

    public void setNombreProfesional(String nombreProfesional) {
        this.nombreProfesional = nombreProfesional;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public SmcDetalleLm getDetalleLM() {
        return detalleLM;
    }

    public void setDetalleLM(SmcDetalleLm detalleLM) {
        this.detalleLM = detalleLM;
    }
}
