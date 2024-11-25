package com.jpa.solicitud.solicitud.models.dto;


public class AprobacionDto {


	private Long solicitudId;
	private Integer rut;

	private String estado;

	// Constructor
	public AprobacionDto(Long idSolicitud, Integer rutFuncionario ) {
		this.solicitudId = idSolicitud;
		this.rut = rutFuncionario;
	}

	// Getters and Setters
	public Long getSolicitudId() {
		return solicitudId;
	}

	public void setSolicitudId(Long idSolicitud) {
		this.solicitudId = idSolicitud;
	}

	public Integer getRut() {
		return rut;
	}

	public void setRut(Integer rutFuncionario) {
		this.rut = rutFuncionario;
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


}
