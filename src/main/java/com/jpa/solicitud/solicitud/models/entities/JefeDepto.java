package com.jpa.solicitud.solicitud.models.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JefeDepto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long depto;
    private String nombreJefe;
    private String cargoJefe;

    public JefeDepto() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDepto() {
        return depto;
    }
    public void setDepto(Long depto) {
        this.depto = depto;
    }
    public String getNombreJefe() {
        return nombreJefe;
    }
    public void setNombreJefe(String nombreJefe) {
        this.nombreJefe = nombreJefe;
    }
    public String getCargoJefe() {
        return cargoJefe;
    }
    public void setCargoJefe(String cargoJefe) {
        this.cargoJefe = cargoJefe;
    }
}
