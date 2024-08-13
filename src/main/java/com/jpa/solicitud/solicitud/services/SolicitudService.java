package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.SolicitudDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudWithDerivacionesDTO;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Entrada;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Rechazo;
import com.jpa.solicitud.solicitud.models.entities.Salida;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.TipoSolicitud;
import com.jpa.solicitud.solicitud.repositories.IAprobacionRepository;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEntradaRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.IRechazoRepository;
import com.jpa.solicitud.solicitud.repositories.ISalidaRepository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.repositories.ITipoSolicitudRepository;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class SolicitudService {

    // *inyección de dependencias de las interfaces repository */

    @Autowired
    private IFuncionarioRespository funcionarioRespository;

    @Autowired
    private ITipoSolicitudRepository tipoSolicitudRepository;

    @Autowired
    private IEstadoRepository estadoRepository;

    @Autowired
    private ISolicitudRespository solicitudRespository;

    @Autowired
    private IDerivacionRepository derivacionRepository;

    @Autowired
    private IDepartamentoRepository departamentoRepository;

    @Autowired
    private IEntradaRepository entradaRepository;

    @Autowired
    private ISalidaRepository salidaRepository;

    @Autowired
    SmcService smcService;

    @Autowired
    private IDepartamentosRepository departamentosRepository;

    @Autowired
    private IRechazoRepository rechazoRepository;

    @Autowired
    private IAprobacionRepository aprobacionRepository;

    @Autowired
    private DerivacionService derivacionService;

    @Autowired
    private SalidaService salidaService;

    // *RestTemplate para la api a smc */
    public SolicitudService(RestTemplate restTemplate) {
    }

    @Transactional
    public Solicitud saveSolicitud(SolicitudDto solicitudDto) {

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicio = solicitudDto.getFechaInicio();
        LocalDate fechaFin = solicitudDto.getFechaFin();
        // Validar los campos necesarios en solicitudDto
        validarSolicitudDto(solicitudDto);

        // Crear y persistir el funcionario
        SmcPersona persona = smcService.getPersonaByRut(solicitudDto.getRut());
        Funcionario funcionario = new Funcionario();
        funcionario.setRut(persona.getRut());
        funcionario.setNombre(StringUtils.buildName(persona.getNombres(), persona.getApellidopaterno(),
                persona.getApellidomaterno()));
        funcionario = funcionarioRespository.save(funcionario);

        // Obtener el tipo de solicitud
        TipoSolicitud tipoSol = obtenerTipoSolicitud(solicitudDto.getTipoSolicitud());

        // Obtener el estado
        Estado estado = obtenerEstado(solicitudDto.getEstado());

        // Crear y persistir la solicitud
        Solicitud solicitud = new Solicitud();
        solicitud.setFuncionario(funcionario);
        solicitud.setTipoSolicitud(tipoSol);
        solicitud.setFechaSolicitud(fechaActual);
        solicitud.setFechaInicio(fechaInicio);
        solicitud.setFechaFin(fechaFin);
        solicitud.setEstado(estado);
        
        solicitud = solicitudRespository.save(solicitud);

        // Crear y persistir la derivación
        Derivacion derivacion = crearDerivacion(solicitudDto, funcionario, solicitud);
        derivacionRepository.save(derivacion);

        return solicitud;
    }

    private void validarSolicitudDto(SolicitudDto solicitudDto) {
        if (solicitudDto == null) {
            throw new IllegalArgumentException("El DTO de solicitud no puede ser nulo");
        }
        // Realizar otras validaciones necesarias
    }

    private TipoSolicitud obtenerTipoSolicitud(String nombreTipoSolicitud) {
        Long idSol = tipoSolicitudRepository.findIdByNombre(nombreTipoSolicitud);
        if (idSol == null) {
            throw new IllegalArgumentException("Tipo de solicitud no encontrado");
        }
        return tipoSolicitudRepository.findById(idSol)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de solicitud no encontrado"));
    }

    private Estado obtenerEstado(String nombreEstado) {
        Long idEstado = estadoRepository.findIdByNombre(nombreEstado);
        if (idEstado == null) {
            throw new IllegalArgumentException("Estado no encontrado");
        }
        return estadoRepository.findById(idEstado)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));
    }

    private Derivacion crearDerivacion(SolicitudDto solicitudDto, Funcionario funcionario, Solicitud solicitud) {
        Departamento departamentoSolicitud = new Departamento();
        Departamentos departamentoRequest = departamentosRepository.findByDepto(solicitudDto.getDepto());
    
        boolean esJefe = departamentosRepository.existsByDeptoIntAndRutJefe(departamentoRequest.getDeptoInt(),
                solicitudDto.getRut());
        if (esJefe) {
            Long codigoDepartamentoDestino = Long
                    .parseLong(DepartamentoUtils.determinaDerivacion(departamentoRequest.getDeptoInt()));
    
            Departamentos departmentSupervisor = departamentosRepository.findByDeptoInt(codigoDepartamentoDestino);
            if (departmentSupervisor == null) {
                throw new IllegalArgumentException("No se encontró el departamento supervisor para el código: " + codigoDepartamentoDestino);
            }
    
            departamentoSolicitud.setDepto(departmentSupervisor.getDeptoInt());
            departamentoSolicitud.setDeptoSmc(departmentSupervisor.getDepto());
            departamentoSolicitud.setNombre(departmentSupervisor.getNombre_departamento());
        } else {
            departamentoSolicitud.setDepto(departamentoRequest.getDeptoInt());
            departamentoSolicitud.setDeptoSmc(departamentoRequest.getDepto());
            departamentoSolicitud.setNombre(departamentoRequest.getNombre_departamento());
        }
    
        // Guardar el departamento antes de asociarlo a la derivación
        departamentoSolicitud = departamentoRepository.save(departamentoSolicitud);

        
    
        Derivacion derivacion = derivacionService.saveDerivacion(departamentoSolicitud, solicitud, funcionario);

        salidaService.saveSalida(derivacion, funcionario);
    
        return derivacion;
    }

    public List<Solicitud> findAll() {
        return solicitudRespository.findAll();
    }

    public List<SolicitudWithDerivacionesDTO> getSolicitudesWithDerivacionesByDepartamento(Long departamentoId) {

        Departamentos departamentos = departamentosRepository.findByDepto(departamentoId);
        // Obtener todas las derivaciones del departamento
        List<Derivacion> derivaciones = derivacionRepository.findByDepartamentoDepto(departamentos.getDeptoInt());

        // Obtener todas las solicitudes a partir de las derivaciones
        List<Solicitud> solicitudes = derivaciones.stream()
                .map(Derivacion::getSolicitud)
                .distinct()
                .collect(Collectors.toList());

        // Crear el DTO para cada solicitud
        return solicitudes.stream().map(solicitud -> {
            List<Derivacion> derivacionesSolicitud = derivacionRepository.findBySolicitudId(solicitud.getId());
            List<Entrada> entradas = derivacionesSolicitud.stream()
                    .flatMap(derivacion -> entradaRepository.findByDerivacionId(derivacion.getId()).stream())
                    .collect(Collectors.toList());
            List<Salida> salidas = derivacionesSolicitud.stream()
                    .flatMap(derivacion -> salidaRepository.findByDerivacion_Id(derivacion.getId()).stream())
                    .collect(Collectors.toList());

            SolicitudWithDerivacionesDTO dto = new SolicitudWithDerivacionesDTO();
            dto.setSolicitud(solicitud);
            dto.setDerivaciones(derivacionesSolicitud);
            dto.setEntradas(entradas);
            dto.setSalidas(salidas);

            Rechazo rechazo = rechazoRepository.findBySolicitudId(solicitud.getId());
            Aprobacion aprobacion = aprobacionRepository.findBySolicitudId(solicitud.getId());
            dto.setRechazo(rechazo);
            dto.setAprobacion(aprobacion);

            return dto;
        }).collect(Collectors.toList());
    }

    public List<Solicitud> servGetSolicitudesPorFuncionario(Integer rut) {

        return solicitudRespository.findByFuncionarioRut(rut);
    }

    public List<Solicitud> servGetSolicitudesPendientesPorFuncionario(Integer rut) {

        return solicitudRespository.findPendingAndNotRejectedByFuncionarioRut(rut);
    }
}
