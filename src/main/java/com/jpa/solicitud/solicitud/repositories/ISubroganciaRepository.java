package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Subrogancia;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ISubroganciaRepository extends JpaRepository<Subrogancia, Long> {
    
    @Query("SELECT s FROM Subrogancia s WHERE s.subrogante.rut = :rutSubrogante AND s.fechaInicio >= :fechaInicio AND s.fechaFin <= :fechaFin")
    List<Subrogancia> findBySubroganteRutAndDates(@Param("rutSubrogante") Integer rutSubrogante,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);


    List<Subrogancia> findBySubrogante(Funcionario subrogante);
}
