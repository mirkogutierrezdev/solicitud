package com.jpa.solicitud.solicitud.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "depto", nullable = false)
    private Long depto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "deptosmc", nullable = false)
    private Long deptoSmc;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getDepto() {
        return depto;
    }

    public void setDepto(Long depto) {
        this.depto = depto;
    }

    public Long getDeptoSmc() {
        return deptoSmc;
    }

    public void setDeptoSmc(Long deptoSmc) {
        this.deptoSmc = deptoSmc;
    }

}
