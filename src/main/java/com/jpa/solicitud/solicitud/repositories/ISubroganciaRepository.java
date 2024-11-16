package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Subrogancia;

import java.util.List;

@Repository
public interface ISubroganciaRepository extends JpaRepository<Subrogancia, Long> {
    List<Subrogancia> findBySubroganteRut(Integer rutSubrogante);
}
