package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.solicitud.solicitud.apimodels.SmcDepartamento;
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

    private final IFuncionarioRespository funcionarioRespository;

    private final ITipoSolicitudRepository tipoSolicitudRepository;

    private final IEstadoRepository estadoRepository;

    private final ISolicitudRespository solicitudRespository;

    private final IDerivacionRepository derivacionRepository;

    private final IDepartamentoRepository departamentoRepository;

    private final IEntradaRepository entradaRepository;

    private final ISalidaRepository salidaRepository;

    private final SmcService smcService;

    private final IDepartamentosRepository departamentosRepository;

    private final IRechazoRepository rechazoRepository;

    private final IAprobacionRepository aprobacionRepository;

    private final DerivacionService derivacionService;

    private final SalidaService salidaService;

    public SolicitudService(IFuncionarioRespository funcionarioRespository,
            ITipoSolicitudRepository tipoSolicitudRepository, IEstadoRepository estadoRepository,
            ISolicitudRespository solicitudRespository, IDerivacionRepository derivacionRepository,
            IDepartamentoRepository departamentoRepository, IEntradaRepository entradaRepository,
            ISalidaRepository salidaRepository, SmcService smcService, IDepartamentosRepository departamentosRepository,
            IRechazoRepository rechazoRepository, IAprobacionRepository aprobacionRepository,
            DerivacionService derivacionService, SalidaService salidaService) {
        this.funcionarioRespository = funcionarioRespository;
        this.tipoSolicitudRepository = tipoSolicitudRepository;
        this.estadoRepository = estadoRepository;
        this.solicitudRespository = solicitudRespository;
        this.derivacionRepository = derivacionRepository;
        this.departamentoRepository = departamentoRepository;
        this.entradaRepository = entradaRepository;
        this.salidaRepository = salidaRepository;
        this.smcService = smcService;
        this.departamentosRepository = departamentosRepository;
        this.rechazoRepository = rechazoRepository;
        this.aprobacionRepository = aprobacionRepository;
        this.derivacionService = derivacionService;
        this.salidaService = salidaService;
    }

    @Transactional
    public Solicitud saveSolicitud(SolicitudDto solicitudDto) {

        LocalDate fechaActual = LocalDate.now();
        LocalDateTime fechaInicio = solicitudDto.getFechaInicio();
        LocalDateTime fechaFin = solicitudDto.getFechaFin();
        Double duracion = solicitudDto.getDuracion();
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
        solicitud.setDuracion(duracion);

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
        SmcDepartamento smcDepartamento = smcService.getDepartamento(solicitudDto.getDepto().toString());
    
        Departamentos departamentoRequest = departamentosRepository
                .findByDepto(Long.parseLong(smcDepartamento.getDepto()));
    
        boolean esJefe = smcService.isJefe(funcionario.getRut());
    
        if (departamentoRequest.getRutJefe() == null || departamentoRequest.getRutJefe().trim().isEmpty()) {
            // Si RutJefe es null, derivar al departamento superior
            Long codigoDepartamentoDestino = Long
                    .parseLong(DepartamentoUtils.determinaDerivacion(departamentoRequest.getDeptoInt()));
    
            Departamentos departmentSupervisor = departamentosRepository.findByDeptoInt(codigoDepartamentoDestino);
            if (departmentSupervisor == null) {
                throw new IllegalArgumentException(
                        "No se encontró el departamento supervisor para el código: " + codigoDepartamentoDestino);
            }
    
            departamentoSolicitud.setDepto(departmentSupervisor.getDeptoInt());
            departamentoSolicitud.setDeptoSmc(departmentSupervisor.getDepto());
            departamentoSolicitud.setNombre(departmentSupervisor.getNombreDepartamento());
        } else if (esJefe) {
            // Si es jefe y RutJefe no es null, derivar al departamento supervisor directo
            Long codigoDepartamentoDestino = Long
                    .parseLong(DepartamentoUtils.determinaDerivacion(departamentoRequest.getDeptoInt()));
    
            Departamentos departmentSupervisor = departamentosRepository.findByDeptoInt(codigoDepartamentoDestino);
            if (departmentSupervisor == null) {
                throw new IllegalArgumentException(
                        "No se encontró el departamento supervisor para el código: " + codigoDepartamentoDestino);
            }
    
            departamentoSolicitud.setDepto(departmentSupervisor.getDeptoInt());
            departamentoSolicitud.setDeptoSmc(departmentSupervisor.getDepto());
            departamentoSolicitud.setNombre(departmentSupervisor.getNombreDepartamento());
        } else {
            // Si no es jefe o no es necesario derivar al superior, mantener en el departamento actual
            departamentoSolicitud.setDepto(departamentoRequest.getDeptoInt());
            departamentoSolicitud.setDeptoSmc(departamentoRequest.getDepto());
            departamentoSolicitud.setNombre(departamentoRequest.getNombreDepartamento());
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
