package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.SubroganciaDto;
import com.jpa.solicitud.solicitud.models.dto.ViewSubroganciaDto;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.Subrogancia;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;
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

    private final IDepartamentosRepository departamentosRepository;

    private final IDepartamentoRepository departamentoRepository;

    public SubroganciaService(ISubroganciaRepository subroganciaRepository, SmcService smcService,
            ISolicitudRespository solicitudRespository, IFuncionarioRespository funcionarioRespository,
            IDepartamentosRepository departamentosRepository, IDepartamentoRepository departamentoRepository) {
        this.subroganciaRepository = subroganciaRepository;
        this.smcService = smcService;
        this.solicitudRespository = solicitudRespository;
        this.funcionarioRespository = funcionarioRespository;
        this.departamentosRepository = departamentosRepository;
        this.departamentoRepository = departamentoRepository;
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

        Departamentos deptos = departamentosRepository.findByRutJefe(jefeDepto.getRut().toString());

        Departamento deptoJefe = new Departamento();
        deptoJefe.setDepto(deptos.getDeptoInt());
        deptoJefe.setDeptoSmc(deptos.getDepto());
        deptoJefe.setNombre(deptos.getNombreDepartamento());

        deptoJefe = departamentoRepository.save(deptoJefe);

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
        subrogancia.setSubDepartamento(deptoJefe);
        subroganciaRepository.save(subrogancia);
        return subrogancia;

    }

    public List<Subrogancia> getSubroganciasByRutSubrogante(Integer rutSubrogante, LocalDate fechaInicio,
            LocalDate fechaFin) {
        return subroganciaRepository.findBySubroganteRutAndDates(rutSubrogante, fechaInicio, fechaFin);
    }

    public List<ViewSubroganciaDto> getSubroganciasByRut(Integer rutSubrogante, LocalDate fechaInicio, LocalDate fechaFin) {

        List<Subrogancia> subrogancias = subroganciaRepository.findBySubroganteRutAndDates(rutSubrogante, fechaInicio, fechaFin);

        return subrogancias.stream()
                .map(sub -> {
                    // Crear instancia del DTO
                    ViewSubroganciaDto dto = new ViewSubroganciaDto();

                    // Validaciones para evitar excepciones por datos nulos
                    dto.setNombreJefe(sub.getJefe() != null ? sub.getJefe().getNombre() : "Sin Jefe");
                    dto.setNombreSubrogante(
                            sub.getSubrogante() != null ? sub.getSubrogante().getNombre() : "Sin Subrogante");
                    dto.setEstadoSolicitud(sub.getSolicitud() != null && sub.getSolicitud().getEstado() != null
                            ? sub.getSolicitud().getEstado().getNombre()
                            : "Estado Desconocido");

                    // Obtener el primer departamento, si existe
                    if (sub.getSolicitud() != null && sub.getSolicitud().getDerivaciones() != null
                            && !sub.getSolicitud().getDerivaciones().isEmpty()) {
                        dto.setDepto(sub.getSolicitud().getDerivaciones().get(0).getDepartamento().getDepto());
                        dto.setNombreDepto(sub.getSolicitud().getDerivaciones().get(0).getDepartamento().getNombre());
                    } else {
                        dto.setDepto(null);
                        dto.setNombreDepto("Departamento Desconocido");
                    }

                    dto.setId(sub.getId());
                    dto.setFechaInicio(sub.getFechaInicio());
                    dto.setFechaFin(sub.getFechaFin());
                    dto.setSubDepartamento(
                            sub.getSubDepartamento() != null ? sub.getSubDepartamento().getDeptoSmc() : null);

                    // Devolver el DTO
                    return dto;
                })
                .toList();
    }

}
