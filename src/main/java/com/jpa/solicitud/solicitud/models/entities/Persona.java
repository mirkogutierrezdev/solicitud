package com.jpa.solicitud.solicitud.models.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Persona {

    @Id
    private Integer rut;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "APELLIDOPATERNO")
    private String apellidopaterno;

    @Column(name = "APELLIDOMATERNO")
    private String apellidomaterno;

    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNac;

 
    // Getters y Setters

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombres(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    
}