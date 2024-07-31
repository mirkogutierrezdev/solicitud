package com.jpa.solicitud.solicitud.models.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vderivaciones")
public class VDerivacion {

    @Id
    private Long solicitud_id; 
    private Date fecha_derivacion;
    private String fun_ori; 
    private String depto_ori;
    private Date fecha_entrada;
    private String fun_ent; 
    private Date fecha_salida; 
    private String fun_sal; 

    // Getters y Setters

    public Long getSolicitudId() {
        return solicitud_id;
    }

    public void setSolicitudId(Long solicitud_id) {
        this.solicitud_id = solicitud_id;
    }

    public Date getFechaDerivacion() {
        return fecha_derivacion;
    }

    public void setFechaDerivacion(Date fecha_derivacion) {
        this.fecha_derivacion = fecha_derivacion;
    }

    public String getFun_ori() {
        return fun_ori;
    }

    public void setFun_ori(String funOri) {
        this.fun_ori = funOri;
    }

    public String getDepto_ori() {
        return depto_ori;
    }

    public void setDepto_ori(String deptoOri) {
        this.depto_ori = deptoOri;
    }

    public Date getFechaEntrada() {
        return fecha_entrada;
    }

    public void setFechaEntrada(Date fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public String getFun_ent() {
        return fun_ent;
    }

    public void setFun_ent(String funEnt) {
        this.fun_ent = funEnt;
    }

    public Date getFechaSalida() {
        return fecha_salida;
    }

    public void setFechaSalida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getFun_sal() {
        return fun_sal;
    }

    public void setFun_sal(String funSal) {
        this.fun_sal = funSal;
    }
}
