package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcDepartamento;
import com.jpa.solicitud.solicitud.models.dto.DerivacionDto;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;

import jakarta.transaction.Transactional;

@Service
public class DerivacionService {

    @Autowired
    private IDerivacionRepository derivacionRepository;

    @Autowired
    private ISolicitudRespository solicitudRespository;

    @Autowired
    private SmcService smcService;

    @Autowired
    private IDepartamentoRepository departamentoRepository;

    @Autowired
    private IEstadoRepository estadoRepository;

    public List<Derivacion> findBySolicitudId(Long id) {
        return derivacionRepository.findBySolicitudId(id);
    }

    public List<Derivacion> findNoLeidas(Long depto) {
        return derivacionRepository.findNoLeidas(depto);
    }

    @Transactional
    public void checkRead(Long idDerivacion, Long idSolicutd, Boolean estado) {
        derivacionRepository.marcarComoNoLeida(idDerivacion, idSolicutd, estado);
    }

    @Transactional
    public Derivacion saveDerivacion(DerivacionDto derivacionDto) {

        Long depto = derivacionDto.getDepto();
        Long idSolicitud = derivacionDto.getIdSolicitud();
        String estado = derivacionDto.getEstado();
        Date fechaDerivacion = derivacionDto.getFechaDerivacion();

        Derivacion derivacion = new Derivacion();

        Optional<Solicitud> solicitud = solicitudRespository.findById(idSolicitud);

        String strDeptoDestino = DepartamentoUtils.determinaDerivacion(depto);

        SmcDepartamento smcDepartamento = smcService.getDepartamento(strDeptoDestino);

        Departamento deptoDestino = new Departamento();

        Long intDepto = Long.parseLong(strDeptoDestino);

        deptoDestino.setDepto(intDepto);
        deptoDestino.setNombre(smcDepartamento.getNombre_departamento());

        deptoDestino = departamentoRepository.save(deptoDestino);

        Estado estadoSol = new Estado();

        Long codEstado = estadoRepository.findIdByNombre(estado);

        estadoSol.setId(codEstado);

        estadoSol.setNombre(estado);

        
        derivacion.setSolicitud(solicitud.get());
        derivacion.setDepartamento(deptoDestino);
        derivacion.setEstado(estadoSol);
        derivacion.setFechaDerivacion(fechaDerivacion);
        derivacion.setComentarios("Prueba de derivacion");
        derivacion.setLeida(false);

        derivacionRepository.save(derivacion);

        return derivacion;

    }

}
