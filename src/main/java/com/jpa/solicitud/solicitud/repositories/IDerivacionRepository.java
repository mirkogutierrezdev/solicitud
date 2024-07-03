package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Derivacion;


@Repository
public interface IDerivacionRepository extends JpaRepository<Derivacion, Long> {

    List<Derivacion> findByDepartamentoCodigo(Long departamentoCodigo);



}
