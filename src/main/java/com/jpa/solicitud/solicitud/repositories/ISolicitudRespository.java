package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Solicitud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ISolicitudRespository extends JpaRepository<Solicitud, Long> {

        Optional<Solicitud>  findById(Long id);

    List<Solicitud> findByFuncionarioRut(Integer rut);

    @Query(value = "SELECT s.* FROM intranetdev.solicitudes s " +
            "LEFT JOIN intranetdev.aprobaciones a ON s.id = a.solicitud_id " +
            "INNER JOIN intranetdev.funcionarios f ON s.funcionario_id = f.id " +
            "WHERE a.solicitud_id IS NULL " +
            "AND s.id NOT IN (SELECT solicitud_id FROM intranetdev.rechazos) " +
            "AND f.rut = :rut", nativeQuery = true)
    List<Solicitud> findPendingAndNotRejectedByFuncionarioRut(@Param("rut") Integer rut);

    @Query(value = """
        SELECT s.* 
        FROM solicitudes s
        JOIN derivaciones d ON s.id = d.solicitud_id
        JOIN departamentos dept ON d.departamento_origen_id = dept.id
        WHERE dept.deptosmc = :departamentoId
          AND DATE(s.fecha_solicitud) BETWEEN :fechaInicio AND :fechaFin
    """, nativeQuery = true)
    List<Solicitud> findSolicitudesByDepartamentoOrigenAndFechas(
            @Param("departamentoId") Long departamentoId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);

}
