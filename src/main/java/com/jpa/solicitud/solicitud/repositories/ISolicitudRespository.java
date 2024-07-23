package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import java.util.List;


@Repository
public interface ISolicitudRespository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByFuncionarioRut(Integer rut);

}
