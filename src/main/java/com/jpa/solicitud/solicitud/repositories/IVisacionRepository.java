package com.jpa.solicitud.solicitud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.Visacion;

@Repository
public interface IVisacionRepository extends JpaRepository<Visacion,Long> {

    Visacion findBySolicitudId(Long id);

    Optional<Visacion> findBySolicitud(Solicitud solicitud);

    Visacion findTopBySolicitudId(Long id);

}