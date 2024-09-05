package com.jpa.solicitud.solicitud.services;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.RechazoDto;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Rechazo;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.IRechazoRepository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class RechazoService {

    private final  SmcService smcService;

    private final IFuncionarioRespository funcionarioRepository;

    private final ISolicitudRespository solicitudRepository;

    private final IEstadoRepository estadoRepository;

    private final IRechazoRepository rechazoRepository;

    

    public RechazoService(SmcService smcService, IFuncionarioRespository funcionarioRepository,
            ISolicitudRespository solicitudRepository, IEstadoRepository estadoRepository,
            IRechazoRepository rechazoRepository) {
        this.smcService = smcService;
        this.funcionarioRepository = funcionarioRepository;
        this.solicitudRepository = solicitudRepository;
        this.estadoRepository = estadoRepository;
        this.rechazoRepository = rechazoRepository;
    }

    public Rechazo saveRechazo(RechazoDto rechazoDto) {

        if (rechazoDto == null) {
            throw new IllegalArgumentException("El objeto AprobacionDto no puede ser null");
        }

        Integer rut = rechazoDto.getRutFuncionario();
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

        Solicitud solicitud = solicitudRepository.findById(rechazoDto.getIdSolicitud()).orElse(null);
        if (solicitud == null) {
            throw new IllegalArgumentException("No se encontró una solicitud con el ID proporcionado");
        }

        String estadoDto = rechazoDto.getEstado();

        Long codEstado = estadoRepository.findIdByNombre(estadoDto);

        Estado estado = new Estado();
        estado.setId(codEstado);
        estado.setNombre(estadoDto);
        estado = estadoRepository.save(estado);

        solicitud.setEstado(estado);

        Rechazo rechazo = new Rechazo();
        rechazo.setFuncionario(funcionario); // Asignar el funcionario guardado
        rechazo.setSolicitud(solicitud);
        rechazo.setFechaRechazo(rechazoDto.getFechaRechazo());
        rechazo.setComentario(rechazoDto.getMotivo());

        // Guardar el objeto Aprobacion en el repositorio
        return rechazoRepository.save(rechazo);

    }

    public Rechazo servGetRechazoBySolicitud(Long solicitudId) {

        return rechazoRepository.findBySolicitudId(solicitudId);
    }
}
