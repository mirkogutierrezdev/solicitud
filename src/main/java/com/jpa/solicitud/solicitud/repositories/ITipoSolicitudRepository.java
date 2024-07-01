package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.TipoSolicitud;

@Repository
public interface ITipoSolicitudRepository extends JpaRepository<TipoSolicitud, Long> {

    TipoSolicitud findByNombre(String nombre);

    @Query("SELECT ts.id FROM TipoSolicitud ts WHERE ts.nombre = :nombre")
    Long findIdByNombre(@Param("nombre") String nombre);

}
