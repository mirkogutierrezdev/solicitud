package com.jpa.solicitud.solicitud.models.dto;

import java.util.List;

public class DecretosDto {


    private List<Long> aprobacionesIds;  // Lista de IDs de las aprobaciones que se asocian al decreto


    public List<Long> getAprobacionesIds() {
        return aprobacionesIds;
    }
    public void setAprobacionesIds(List<Long> aprobacionesIds) {
        this.aprobacionesIds = aprobacionesIds;
    }


    
    
}
