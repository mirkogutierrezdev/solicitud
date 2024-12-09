package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findByRut(String rut);

}
