package com.jpa.solicitud.solicitud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;

@Repository
public interface IAprobacionRepository extends JpaRepository<Aprobacion, Long> {
	// Puedes agregar métodos de consulta personalizados aquí si es necesario
	Aprobacion findBySolicitudId(Long idSolicitud);
}