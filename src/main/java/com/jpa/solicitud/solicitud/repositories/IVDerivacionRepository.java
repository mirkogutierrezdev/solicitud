package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.VDerivacion;

@Repository
public interface IVDerivacionRepository extends JpaRepository<VDerivacion, Long> {
    
    @Query(value = "SELECT * FROM vderivaciones WHERE solicitud_id = :solicitudId", nativeQuery = true)
    List<VDerivacion> findBySolicitudIdNative(@Param("solicitudId") Long solicitudId);
    

}
