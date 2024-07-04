package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Derivacion;


@Repository
public interface IDerivacionRepository extends JpaRepository<Derivacion, Long> {

    List<Derivacion> findByDepartamentoCodigo(Long departamentoCodigo);

    @Query("SELECT d FROM Derivacion d " +
           "JOIN FETCH d.solicitud s " +
           "JOIN FETCH s.funcionario f " +
           "WHERE f.rut = :rut")
    List<Derivacion> findByFuncionarioRut(@Param("rut") Integer rut);



}
