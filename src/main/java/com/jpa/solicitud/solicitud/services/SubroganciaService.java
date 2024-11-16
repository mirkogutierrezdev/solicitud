package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.SubroganciaDto;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.Subrogancia;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.repositories.ISubroganciaRepository;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class SubroganciaService {

    private final ISubroganciaRepository subroganciaRepository;

    private final SmcService smcService;

    private final ISolicitudRespository solicitudRespository;

    private final IFuncionarioRespository funcionarioRespository;

    public SubroganciaService(ISubroganciaRepository subroganciaRepository, SmcService smcService,
            ISolicitudRespository solicitudRespository, IFuncionarioRespository funcionarioRespository) {
        this.subroganciaRepository = subroganciaRepository;
        this.smcService = smcService;
        this.solicitudRespository = solicitudRespository;
        this.funcionarioRespository = funcionarioRespository;
    }

    public Subrogancia saveSubrogancia(SubroganciaDto subroganciaDto) {

        Integer rutJefe = subroganciaDto.getRutJefe();
        Integer rutSubrogante = subroganciaDto.getRutSubrogante();
        Long idSolicitud = subroganciaDto.getIdSolicitud();
        LocalDate fechaInicio = subroganciaDto.getFechaInicio();
        LocalDate fechaFin = subroganciaDto.getFechaFin();

        SmcPersona personaJefe = smcService.getPersonaByRut(rutJefe);
        SmcPersona personaSubrogante = smcService.getPersonaByRut(rutSubrogante);

        Funcionario jefeDepto = new Funcionario();
        jefeDepto.setRut(personaJefe.getRut());
        jefeDepto.setNombre(StringUtils.buildName(personaJefe.getNombres(), personaJefe.getApellidopaterno(),
                personaJefe.getApellidomaterno()));

                jefeDepto = funcionarioRespository.save(jefeDepto);

        Funcionario subrogante = new Funcionario();


        subrogante.setRut(personaSubrogante.getRut());
        subrogante.setNombre(StringUtils.buildName(personaSubrogante.getNombres(),
                personaSubrogante.getApellidopaterno(), personaSubrogante.getApellidomaterno()));

        subrogante = funcionarioRespository.save(subrogante);

        Optional<Solicitud> optSolicitud = solicitudRespository.findById(idSolicitud);

        Solicitud solicitud = optSolicitud.orElseThrow(() -> new IllegalArgumentException("Valor no presente"));

        Subrogancia subrogancia = new Subrogancia();
        subrogancia.setJefe(jefeDepto);
        subrogancia.setSubrogante(subrogante);
        subrogancia.setSolicitud(solicitud);
        subrogancia.setFechaInicio(fechaInicio);
        subrogancia.setFechaFin(fechaFin);
        subroganciaRepository.save(subrogancia);
        return subrogancia;

    }

    public List<Subrogancia> getSubroganciasByRutSubrogante(Integer rutSubrogante) {
        return subroganciaRepository.findBySubroganteRut(rutSubrogante);
    }

}
