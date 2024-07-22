package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Rechazo;

@Repository
public interface IRechazoRepository extends JpaRepository<Rechazo, Long> { 

    Rechazo findBySolicitudId(Long idSolicitud);

}
