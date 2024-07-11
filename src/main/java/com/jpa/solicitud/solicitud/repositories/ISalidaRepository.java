package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.solicitud.solicitud.models.entities.Salida;

public interface ISalidaRepository extends JpaRepository<Salida,Long> {

    List<Salida> findByDerivacion_Id(Long id);
}
