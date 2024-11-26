package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.solicitud.solicitud.models.entities.Entrada;

public interface IEntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findByDerivacionId(Long id);
    

    

}
