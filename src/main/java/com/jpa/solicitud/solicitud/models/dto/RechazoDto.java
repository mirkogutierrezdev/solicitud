package com.jpa.solicitud.solicitud.models.dto;

import java.sql.Date;

public class RechazoDto {
	private Long idSolicitud;
	private Integer rutFuncionario;
	private Date fechaRechazo;
	private String estado;

	// Constructor
	public RechazoDto(Long idSolicitud, Integer rutFuncionario, Date fechaAprobacion) {
		this.idSolicitud = idSolicitud;
		this.rutFuncionario = rutFuncionario;
		this.fechaRechazo = fechaAprobacion;
	}

	// Getters and Setters
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Integer getRutFuncionario() {
		return rutFuncionario;
	}

	public void setRutFuncionario(Integer rutFuncionario) {
		this.rutFuncionario = rutFuncionario;
	}

	public Date getFechaRechazo() {
		return fechaRechazo;
	}

	public void setFechaRechazo(Date fechaAprobacion) {
		this.fechaRechazo = fechaAprobacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


}
