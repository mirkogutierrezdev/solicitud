package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jpa.solicitud.solicitud.models.entities.Salida;

public interface ISalidaRepository extends JpaRepository<Salida,Long> {

    @Query("SELECT s FROM Salida s " +
       "JOIN FETCH s.derivacion d " +
       "JOIN FETCH d.solicitud sol " +
       "JOIN FETCH sol.funcionario f " +
       "JOIN FETCH d.departamento de " +
       "WHERE d.departamento.depto = :depto")
List<Salida> findSalidaByDepto(@Param("depto") Long depto);
}
