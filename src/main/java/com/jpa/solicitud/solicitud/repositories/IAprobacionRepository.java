package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;

@Repository
public interface IAprobacionRepository extends JpaRepository<Aprobacion, Long> {
	// Puedes agregar métodos de consulta personalizados aquí si es necesario
	Aprobacion findBySolicitudId(Long idSolicitud);

	List<Aprobacion> findAll();

	@Query("SELECT a FROM Aprobacion a JOIN FETCH a.solicitud")
	List<Aprobacion> findAllWithSolicitud();

	  // Nueva consulta para obtener todas las aprobaciones sin decreto
	  @Query("SELECT a FROM Aprobacion a WHERE a.decreto IS NULL")
	  List<Aprobacion> findAllWithoutDecreto();

}