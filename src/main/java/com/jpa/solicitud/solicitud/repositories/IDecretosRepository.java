package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Decretos;

@Repository
public interface IDecretosRepository extends JpaRepository<Decretos, Long> {

    @Query("SELECT d FROM Decretos d JOIN FETCH d.aprobaciones a WHERE a.decreto IS NULL AND d.id = :id")
    Decretos findDecretosWithOutAprobacion(@Param("id") Long id);

    List<Decretos> findAll();

}
