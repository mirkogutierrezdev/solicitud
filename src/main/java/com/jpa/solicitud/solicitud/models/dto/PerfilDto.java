package com.jpa.solicitud.solicitud.models.dto;

import java.util.Set;

public class PerfilDto {

    private String nombre;
    private String descripcion;

    Set<Long> idPermisos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Long> getIdPermisos() {
        return idPermisos;
    }

    public void setIdPermisos(Set<Long> idPermisos) {
        this.idPermisos = idPermisos;
    }


    

}
