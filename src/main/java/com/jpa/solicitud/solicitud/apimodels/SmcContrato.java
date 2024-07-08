package com.jpa.solicitud.solicitud.apimodels;

import java.sql.Date;

public class SmcContrato {

    private Date fechainicio;
    private Date fechatermino;
    private int licontrato;
    private String depto;
    private String escalafon;
    private String nombrecontrato;
    private Integer grado;
    private Integer isJefe;

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechatermino() {
        return fechatermino;
    }

    public void setFechatermino(Date fechatermino) {
        this.fechatermino = fechatermino;
    }

    public int getLicontrato() {
        return licontrato;
    }

    public void setLicontrato(int licontrato) {
        this.licontrato = licontrato;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getEscalafon() {
        return escalafon;
    }

    public void setEscalafon(String escalafon) {
        this.escalafon = escalafon;
    }

    public String getNombrecontrato() {
        return nombrecontrato;
    }

    public void setNombrecontrato(String nombrecontrato) {
        this.nombrecontrato = nombrecontrato;
    }

    public Integer getGrado() {
        return grado;
    }

    public void setGrado(Integer grado) {
        this.grado = grado;
    }

    public Integer getIsJefe() {
        return isJefe;
    }

    public void setIsJefe(Integer isJefe) {
        this.isJefe = isJefe;
    }
}
