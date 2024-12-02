package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Perfil;

@Repository
public interface IPerfilRepository extends  JpaRepository<Perfil,Long> {



}
