package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.solicitud.solicitud.models.entities.Solicitud;

public interface ISolicitudRespository extends JpaRepository<Solicitud,Long> {

}
