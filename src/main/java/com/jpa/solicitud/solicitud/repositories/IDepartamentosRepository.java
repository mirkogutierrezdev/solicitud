package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Departamentos;

@Repository
public interface IDepartamentosRepository extends JpaRepository<Departamentos, Long> {

    Departamentos findByDepto(Long Depto);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM Departamentos d WHERE d.deptoInt = :depto and d.rut_jefe= :rut")
    boolean existsByDeptoIntAndRutJefe(@Param("depto") Long deptoInt, @Param("rut") Integer rut);

    

}
