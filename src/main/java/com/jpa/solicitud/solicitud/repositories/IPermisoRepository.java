package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.solicitud.solicitud.models.entities.Permiso;

public interface IPermisoRepository extends JpaRepository<Permiso,Long> {

}
