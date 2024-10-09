package com.jpa.solicitud.solicitud.models.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "decretos")
public class Decretos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nroDecreto;

    private LocalDate fechaDecreto;
    @OneToMany(mappedBy = "decreto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Maneja la serialización de la lista de aprobaciones
    private List<Aprobacion> aprobaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNroDecreto() {
        return nroDecreto;
    }

    public void setNroDecreto(Integer nroDecreto) {
        this.nroDecreto = nroDecreto;
    }

    public LocalDate getFechaDecreto() {
        return fechaDecreto;
    }

    public void setFechaDecreto(LocalDate fechaDecreto) {
        this.fechaDecreto = fechaDecreto;
    }

    public List<Aprobacion> getAprobaciones() {
        return aprobaciones;
    }

    public void setAprobaciones(List<Aprobacion> aprobaciones) {
        this.aprobaciones = aprobaciones;
    }

}
