package com.jpa.solicitud.solicitud.models.dto;

import java.sql.Date;

public class EntradaDto {


    private Long solicitudId;
    private Long funcionarioId;
    private Long derivacionId;
    private Date fechaEntrada;
    public Long getSolicitudId() {
        return solicitudId;
    }
    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
    }
    public Long getFuncionarioId() {
        return funcionarioId;
    }
    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
    public Long getDerivacionId() {
        return derivacionId;
    }
    public void setDerivacionId(Long derivacionId) {
        this.derivacionId = derivacionId;
    }
    public Date getFechaEntrada() {
        return fechaEntrada;
    }
    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    


}
