package com.jpa.solicitud.solicitud.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.dto.VDerivacionDto;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;

@Repository
public interface IDerivacionRepository extends JpaRepository<Derivacion, Long> {

        List<Derivacion> findByDepartamentoId(Long departamentoId);

        List<Derivacion> findByDepartamentoDepto(Long depto);

        List<Derivacion> findBySolicitudId(Long solicitudId);

        @SuppressWarnings("null")
        Optional<Derivacion> findById(Long id);

        @Query("SELECT new com.jpa.solicitud.solicitud.models.dto.VDerivacionDto(d.id, d.fechaDerivacion, " +
                        "funOri.nombre, deptoOri.nombre, e.fechaEntrada, funEnt.nombre, s.fechaSalida, funSal.nombre) "
                        +
                        "FROM Derivacion d " +
                        "LEFT JOIN Salida s ON s.derivacion.id = d.id " +
                        "LEFT JOIN Entrada e ON e.derivacion.id = d.id " +
                        "LEFT JOIN Funcionario funOri ON funOri.id = d.funcionario.id " +
                        "LEFT JOIN Funcionario funEnt ON funEnt.id = e.funcionario.id " +
                        "LEFT JOIN Funcionario funSal ON funSal.id = s.funcionario.id " +
                        "LEFT JOIN Departamento deptoOri ON deptoOri.id = d.departamento.id " +
                        "WHERE d.solicitud.id = :solicitudId")
        List<VDerivacionDto> findDerivacionesBySolicitud(@Param("solicitudId") Long solicitudId);

        @Query("SELECT d FROM Derivacion d " +
                        "JOIN d.solicitud s " +
                        "WHERE d.departamento.depto = :departamentoId " +
                        "AND s.fechaInicio >= :fechaInicio " +
                        "AND s.fechaFin <= :fechaFin")
        List<Derivacion> findByDepartamentoAndSolicitudDates(
                        @Param("departamentoId") Long departamentoId,
                        @Param("fechaInicio") LocalDateTime fechaInicio,
                        @Param("fechaFin") LocalDateTime fechaFin);

}
