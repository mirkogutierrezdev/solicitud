package com.jpa.solicitud.solicitud.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;

@Service
public class DepartamentosService {

    private final IDepartamentosRepository departamentosRepository;

    

    public DepartamentosService(IDepartamentosRepository departamentosRepository) {
        this.departamentosRepository = departamentosRepository;
    }

    public List<Departamentos> saveAllDepartamentos(List<Departamentos> departamentos) {

        return departamentosRepository.saveAll(departamentos);
    }

    public boolean existsByDeptoIntAndRutJefe(Long depto, Integer rut) {

        Departamentos deptoSmc = departamentosRepository.findByDepto(depto);

        Long deptoInt = deptoSmc.getDeptoInt();

        return departamentosRepository.existsByDeptoIntAndRutJefe(deptoInt, rut);
    }

    public Departamentos findByDepto(Long depto) {

        return departamentosRepository.findByDepto(depto);
    }

    public boolean esSub(Long depto) {

        Departamentos departamentos = departamentosRepository.findByDepto(depto);
        Long deptos = departamentos.getDeptoInt();
        String deptoInt = deptos.toString();

        return DepartamentoUtils.esSubdir(deptoInt);
    }
}
