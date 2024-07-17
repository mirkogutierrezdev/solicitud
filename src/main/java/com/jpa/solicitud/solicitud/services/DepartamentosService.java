package com.jpa.solicitud.solicitud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;

@Service
public class DepartamentosService {

    @Autowired
    private IDepartamentosRepository departamentosRepository;

    public List<Departamentos> saveAllDepartamentos(List<Departamentos> departamentos) {

        return departamentosRepository.saveAll(departamentos);

    }

    public boolean existsByDeptoIntAndRutJefe(Long depto,Integer rut){

        Departamentos deptoSmc = departamentosRepository.findByDepto(depto);

        Long deptoInt = deptoSmc.getDeptoInt();

        return departamentosRepository.existsByDeptoIntAndRutJefe(deptoInt, rut);
    }

}
