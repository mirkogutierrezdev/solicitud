package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Departamento;

@Repository
public interface IDepartamentoRepository extends JpaRepository<Departamento,Long> {

    @Query("SELECT d.depto FROM Departamento d WHERE d.depto = :depto")
     Departamento findFirstByDepto(@Param("depto")Long depto);

}
