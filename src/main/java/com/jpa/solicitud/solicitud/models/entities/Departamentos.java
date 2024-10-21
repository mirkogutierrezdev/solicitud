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
    private Long depto;

    @Column(name = "nombre_depto_smc", nullable = true)
    private String nombreDepartamento;

    @Column(name = "jefe_departamento_smc", nullable = true)
    private String jefeDepartamento;

    @Column(name = "cargo_departamento_smc", nullable = true)
    private String cargoJefe;

    @Column(name = "rut_jefe", nullable = true)
    private String rutJefe;

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

    public Long getDepto() {
        return depto;
    }

    public void setDepto(Long depto) {
        this.depto = depto;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getJefeDepartamento() {
        return jefeDepartamento;
    }

    public void setJefeDepartamento(String jefeDepartamento) {
        this.jefeDepartamento = jefeDepartamento;
    }

    public String getCargoJefe() {
        return cargoJefe;
    }

    public void setCargoJefe(String cargoJefe) {
        this.cargoJefe = cargoJefe;
    }

    public String getRutJefe() {
        return rutJefe;
    }

    public void setRutJefe(String rutJefe) {
        this.rutJefe = rutJefe;
    }

}
