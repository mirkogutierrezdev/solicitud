package com.jpa.solicitud.solicitud.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.jpa.solicitud.solicitud.models.entities.Persona;

@Repository
public interface IPersonaRepository extends CrudRepository<Persona, Integer> {

    Persona findByRut(Integer rut);

}
