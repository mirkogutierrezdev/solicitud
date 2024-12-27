package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Funcionario;

import java.util.Optional;

@Repository
public interface IFuncionarioRespository extends JpaRepository<Funcionario, Long> {

    @SuppressWarnings("null")
    Optional<Funcionario> findById(Long id);

    // Agregar método para buscar por RUT
    Optional<Funcionario> findByRut(Integer rut);
}
