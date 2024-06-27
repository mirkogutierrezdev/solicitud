package com.jpa.solicitud.solicitud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.entities.Persona;
import com.jpa.solicitud.solicitud.repositories.IPersonaRepository;

@Service
public class PersonaService {

    @Autowired
    private IPersonaRepository personaRepository;

    public Persona findByRut(Integer rut){

        return personaRepository.findByRut(rut);
    }

    public Persona guardaPersona(Persona persona){
        personaRepository.save(persona);

        return persona;

    }




}
