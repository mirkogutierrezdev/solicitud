package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.solicitud.solicitud.models.entities.Entrada;

public interface IEntradaRepository extends JpaRepository<Entrada, Long> {
    @Query("SELECT e FROM Entrada e " +
            "JOIN FETCH e.derivacion d " +
            "JOIN FETCH d.solicitud s " +
            "JOIN FETCH s.funcionario f " +
            "JOIN FETCH d.departamento de " +
            "WHERE d.departamento.depto = :depto")
    List<Entrada> findEntradaByDepto(@Param("depto") Long depto);

}
