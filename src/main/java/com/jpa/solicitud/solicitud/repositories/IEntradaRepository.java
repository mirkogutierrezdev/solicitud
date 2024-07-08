package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.solicitud.solicitud.models.entities.Entrada;

public interface IEntradaRepository extends JpaRepository<Entrada,Long> {

}
