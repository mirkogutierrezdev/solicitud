package com.jpa.solicitud.solicitud.models.dto;

import java.time.LocalDate;

public class ViewSubroganciaDto {
    private Long id;
    private String nombreJefe;
    private String nombreSubrogante;
    private String nombreDepto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // Constructor


    public Long getId() {
        return id;
    }

  

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreJefe() {
        return nombreJefe;
    }

    public void setNombreJefe(String nombreJefe) {
        this.nombreJefe = nombreJefe;
    }

    public String getNombreSubrogante() {
        return nombreSubrogante;
    }

    public void setNombreSubrogante(String nombreSubrogante) {
        this.nombreSubrogante = nombreSubrogante;
    }


    public String getNombreDepto() {
        return nombreDepto;
    }

    public void setNombreDepto(String nombreDepto) {
        this.nombreDepto = nombreDepto;
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
