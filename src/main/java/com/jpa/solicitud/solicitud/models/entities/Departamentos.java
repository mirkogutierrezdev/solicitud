package com.jpa.solicitud.solicitud.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tabladepartamentos")
public class Departamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "depto", nullable = true)
    private Long deptoInt;

    @Column(name = "depto_smc", nullable = true)
    private String depto;

    @Column(name = "nombre_depto_smc", nullable = true)
    private String nombre_departamento;

    @Column(name = "jefe_departamento_smc", nullable = true)
    private String jefe_departamento;

    @Column(name = "cargo_departamento_smc", nullable = true)
    private String cargo_jefe;

    @Column(name = "rut_jefe",nullable = true)
    private Integer rutJefe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptoInt() {
        return deptoInt;
    }

    public void setDeptoInt(Long deptoInt) {
        this.deptoInt = deptoInt;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public void setNombre_departamento(String nombre_departamento) {
        this.nombre_departamento = nombre_departamento;
    }

    public String getJefe_departamento() {
        return jefe_departamento;
    }

    public void setJefe_departamento(String jefe_departamento) {
        this.jefe_departamento = jefe_departamento;
    }

    public String getCargo_jefe() {
        return cargo_jefe;
    }

    public void setCargo_jefe(String cargo_jefe) {
        this.cargo_jefe = cargo_jefe;
    }

    public Integer getRutJefe() {
        return rutJefe;
    }

    public void setRutJefe(Integer rutJefe) {
        this.rutJefe = rutJefe;
    }

   

}
