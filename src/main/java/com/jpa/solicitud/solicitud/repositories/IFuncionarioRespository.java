package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.solicitud.solicitud.models.entities.Funcionario;

public interface IFuncionarioRespository extends JpaRepository<Funcionario,Long> {

}
