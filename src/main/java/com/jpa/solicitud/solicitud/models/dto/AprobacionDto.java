package com.jpa.solicitud.solicitud.models.dto;

import java.sql.Date;

public class AprobacionDto {
	private Long idSolicitud;
	private Integer rutFuncionario;
	private Date fechaAprobacion;

	// Constructor
	public AprobacionDto(Long idSolicitud, Integer rutFuncionario, Date fechaAprobacion) {
		this.idSolicitud = idSolicitud;
		this.rutFuncionario = rutFuncionario;
		this.fechaAprobacion = fechaAprobacion;
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

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}


}
