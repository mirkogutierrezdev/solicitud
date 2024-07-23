package com.jpa.solicitud.solicitud.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.AprobacionDto;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.repositories.IAprobacionRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class AprobacionService {

    @Autowired
    private IAprobacionRepository aprobacionRepository;

    @Autowired
    private IFuncionarioRespository funcionarioRepository;

    @Autowired
    private SmcService smcService;

    @Autowired
    private ISolicitudRespository solicitudRepository;

    @Autowired
    private IEstadoRepository estadoRepository;

    @Autowired
    private IDerivacionRepository derivacionRepository;

    public Aprobacion saveAprobacion(AprobacionDto aprobacionDto) {

        if (aprobacionDto == null) {
            throw new IllegalArgumentException("El objeto AprobacionDto no puede ser null");
        }

        Integer rut = aprobacionDto.getRutFuncionario();
        SmcPersona persona = smcService.getPersonaByRut(rut);
        if (persona == null) {
            throw new IllegalArgumentException("No se encontró un funcionario con el RUT proporcionado");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setRut(persona.getRut());
        funcionario.setNombre(
                StringUtils.buildName(persona.getNombres(), persona.getApellidopaterno(),
                        persona.getApellidomaterno()));
        funcionario = funcionarioRepository.save(funcionario); // Guardar el funcionario antes de asignarlo

        Solicitud solicitud = solicitudRepository.findById(aprobacionDto.getIdSolicitud()).orElse(null);
        if (solicitud == null) {
            throw new IllegalArgumentException("No se encontró una solicitud con el ID proporcionado");
        }

        List<Derivacion> derivaciones = derivacionRepository.findBySolicitudId(solicitud.getId());

        if (derivaciones.isEmpty()) {
            throw new IllegalArgumentException("No hay derivaciones asociadas a la solicitud proporcionada.");
        }

        Derivacion ultimaDerivacion = derivaciones.stream()
                .max(Comparator.comparing(Derivacion::getId))
                .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar la última derivación."));

        ultimaDerivacion.setLeida(true);

        String estadoDto = aprobacionDto.getEstado();

        Long codEstado = estadoRepository.findIdByNombre(estadoDto);

        Estado estado = new Estado();
        estado.setId(codEstado);
        estado.setNombre(estadoDto);
        estado = estadoRepository.save(estado);

        solicitud.setEstado(estado);

        Aprobacion aprobacion = new Aprobacion();
        aprobacion.setFuncionario(funcionario); // Asignar el funcionario guardado
        aprobacion.setSolicitud(solicitud);
        aprobacion.setFechaAprobacion(aprobacionDto.getFechaAprobacion());

        // Guardar el objeto Aprobacion en el repositorio
        return aprobacionRepository.save(aprobacion);
    }


    public Aprobacion servGetAprobacionBySolicitud(Long solicitudId){

        return aprobacionRepository.findBySolicitudId(solicitudId);
    }
}
