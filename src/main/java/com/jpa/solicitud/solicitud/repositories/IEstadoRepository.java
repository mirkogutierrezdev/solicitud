package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.solicitud.solicitud.models.entities.Estado;


public interface IEstadoRepository extends JpaRepository<Estado,Long> {

    
    Estado findByNombre(String nombre);

    @Query("SELECT e.id FROM Estado e WHERE e.nombre = :nombre")
    Long findIdByNombre(@Param("nombre") String nombre);

}
