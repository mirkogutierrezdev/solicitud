package com.jpa.solicitud.solicitud.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Subrogancia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jefe_id", nullable = false)
    private Funcionario jefe;

    @ManyToOne
    @JoinColumn(name = "subrogante_id", nullable = false)
    private Funcionario subrogante;

    @OneToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud; // Relación con la solicitud de vacaciones u otro tipo de solicitud de ausencia.

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Funcionario getJefe() {
        return jefe;
    }

    public void setJefe(Funcionario jefe) {
        this.jefe = jefe;
    }

    public Funcionario getSubrogante() {
        return subrogante;
    }

    public void setSubrogante(Funcionario subrogante) {
        this.subrogante = subrogante;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }


}
