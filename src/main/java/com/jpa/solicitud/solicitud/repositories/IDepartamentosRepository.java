package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Departamentos;

@Repository
public interface IDepartamentosRepository extends JpaRepository<Departamentos,Long> {

}
