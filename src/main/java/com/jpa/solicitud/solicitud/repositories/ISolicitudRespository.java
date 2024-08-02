package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import java.util.List;


@Repository
public interface ISolicitudRespository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByFuncionarioRut(Integer rut);

     @Query(value = "SELECT s.* FROM intranetdev.solicitudes s " +
                   "LEFT JOIN intranetdev.aprobaciones a ON s.id = a.solicitud_id " +
                   "INNER JOIN intranetdev.funcionarios f ON s.funcionario_id = f.id " +
                   "WHERE a.solicitud_id IS NULL " +
                   "AND s.id NOT IN (SELECT solicitud_id FROM intranetdev.rechazos) " +
                   "AND f.rut = :rut", nativeQuery = true)
    List<Solicitud> findPendingAndNotRejectedByFuncionarioRut(@Param("rut") Integer rut);

}
