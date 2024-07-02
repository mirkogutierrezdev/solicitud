package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Derivacion;


@Repository
public interface IDerivacionRepository extends JpaRepository<Derivacion, Long> {

    List<Derivacion> findByDepartamentoCodigo(Long departamentoCodigo);

    @Query(value = "SELECT new com.jpa.solicitud.solicitud.models.dto.SolicitudDerivacionDto(s.id, s.funcionario.id, s.fechaSolicitud, s.fechaInicio, s.fechaFin, s.tipoSolicitud.id, s.estado.id, d.id, d.fechaDerivacion, d.departamentoCodigo, d.comentarios, f.rut, e.nombre, t.nombre) " +
                   "FROM Derivacion d " +
                   "JOIN d.solicitud s " +
                   "JOIN s.funcionario f " +
                   "JOIN s.estado e " +
                   "JOIN s.tipoSolicitud t " +
                   "WHERE d.departamentoCodigo = :departamentoCodigo")
    List<Derivacion> findSolicitudesByDepartamentoCodigo(@Param("departamentoCodigo") Long departamentoCodigo);

}
