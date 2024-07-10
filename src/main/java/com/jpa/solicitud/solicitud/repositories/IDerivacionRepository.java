package com.jpa.solicitud.solicitud.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.solicitud.solicitud.models.entities.Derivacion;

import jakarta.transaction.Transactional;

@Repository
public interface IDerivacionRepository extends JpaRepository<Derivacion, Long> {

        @Query("SELECT d FROM Derivacion d " +
                        "JOIN FETCH d.solicitud s " +
                        "JOIN FETCH s.funcionario f " +
                        "JOIN FETCH d.departamento de " +
                        "WHERE d.departamento.depto = :depto" )
        List<Derivacion> findByDepartamentoDepto(@Param("depto") Long depto);

        @Query("SELECT d FROM Derivacion d " +
                        "JOIN FETCH d.solicitud s " +
                        "JOIN FETCH s.funcionario f " +
                        "WHERE f.rut = :rut")
        List<Derivacion> findByFuncionarioRut(@Param("rut") Integer rut);

        @Query("SELECT d FROM Derivacion d " +
                        "JOIN FETCH d.solicitud s " +
                        "JOIN FETCH s.funcionario f " +
                        "JOIN FETCH d.departamento de " +
                        "WHERE s.id = :id")
        List<Derivacion> findBySolicitudId(@Param("id") Long id);

        @Query("SELECT d FROM Derivacion d " +
                        "JOIN FETCH d.solicitud s " +
                        "JOIN FETCH s.funcionario f " +
                        "JOIN FETCH d.departamento de " +
                        "WHERE d.departamento.depto = :depto AND d.leida = false")
        List<Derivacion> findNoLeidas(@Param("depto") Long depto);

        @Transactional
        @Modifying
        @Query("UPDATE Derivacion d SET d.leida = :estado WHERE d.id = :derivacionId AND d.solicitud.id = :solicitudId")
        void marcarComoNoLeida(@Param("derivacionId") Long idDerivacion, @Param("solicitudId") Long idSolicitud,
                        @Param("estado") Boolean estado);

        @Query("SELECT d FROM Derivacion d " +
                        "JOIN FETCH d.solicitud s " +
                        "JOIN FETCH s.funcionario f " +
                        "JOIN FETCH d.departamento de " +
                        "WHERE d.id = :idDerivacion AND f.id = :funcionarioId")
        Derivacion findDerivacionByIdAndFuncionario(@Param("idDerivacion") Long idDerivacion,
                        @Param("funcionarioId") Long funcionarioId);

}
