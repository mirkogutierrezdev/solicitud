package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cglib.core.Local;
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
import com.jpa.solicitud.solicitud.models.entities.Subrogancia;
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
import com.jpa.solicitud.solicitud.repositories.ISubroganciaRepository;
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

    private final SalidaService salidaService;

    private final ApiService apiService;

    private final ISubroganciaRepository subroganciaRepository;

    public SolicitudService(IFuncionarioRespository funcionarioRespository,
            ITipoSolicitudRepository tipoSolicitudRepository, IEstadoRepository estadoRepository,
            ISolicitudRespository solicitudRespository, IDerivacionRepository derivacionRepository,
            IDepartamentoRepository departamentoRepository, IEntradaRepository entradaRepository,
            ISalidaRepository salidaRepository, SmcService smcService, IDepartamentosRepository departamentosRepository,
            IRechazoRepository rechazoRepository, IAprobacionRepository aprobacionRepository,
            SalidaService salidaService, ApiService apiService,
            ISubroganciaRepository subroganciaRepository) {
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
        this.salidaService = salidaService;
        this.apiService = apiService;
        this.subroganciaRepository = subroganciaRepository;
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
        SmcDepartamento smcDepartamento = smcService.getDepartamento(solicitudDto.getDepto().toString());
        Departamentos departamentoRequest = departamentosRepository
                .findByDepto(Long.parseLong(smcDepartamento.getDepto()));

        if (departamentoRequest == null) {
            throw new IllegalArgumentException(
                    "No se encontró el departamento con código: " + smcDepartamento.getDepto());
        }

        // Crear el departamento de origen
        Departamento departamentoOrigen = new Departamento();
        departamentoOrigen.setDepto(departamentoRequest.getDeptoInt());
        departamentoOrigen.setDeptoSmc(departamentoRequest.getDepto());
        departamentoOrigen.setNombre(departamentoRequest.getNombreDepartamento());

        // Guardar el departamento de origen (si no existe)
        departamentoOrigen = departamentoRepository.save(departamentoOrigen);

        // Determinar el departamento destino
        Departamento departamentoDestino = determinarDepartamentoDestino(departamentoRequest, funcionario);

        // Guardar el departamento destino (si no existe)
        departamentoDestino = departamentoRepository.save(departamentoDestino);

        // Crear y guardar la derivación, incluyendo el departamento de origen y destino
        Derivacion derivacion = new Derivacion();
        derivacion.setSolicitud(solicitud);
        derivacion.setFuncionario(funcionario);
        derivacion.setFechaDerivacion(Date.valueOf(LocalDate.now()));
        derivacion.setLeida(false);
        derivacion.setDepartamento(departamentoDestino); // Departamento de destino
        derivacion.setDepartamentoOrigen(departamentoOrigen); // Departamento de origen

        derivacion = derivacionRepository.save(derivacion);

        // Crear y guardar la salida asociada
        salidaService.saveSalida(derivacion, funcionario);

        String mail = getMail(departamentoDestino.getDepto());
        String nombreJefe = getNombreJefe(departamentoDestino.getDepto());

        apiService.sendEmail("1", nombreJefe,
                solicitud.getTipoSolicitud().getNombre(), mail);

        return derivacion;
    }

    private Departamento determinarDepartamentoDestino(Departamentos departamentoRequest, Funcionario funcionario) {
        Departamentos deptoActual = obtenerDepartamentoInicial(departamentoRequest);
        Integer rutFuncionario = obtenerRutFuncionario(funcionario);

        if (rutFuncionario == null) {
            return mapearDepartamento(deptoActual);
        }

        if(rutFuncionario.equals(16091531)){
            return mapearDepartamento(deptoActual);
        }

        Departamentos deptoEnRevision = buscarDepartamentoConJefeValido(deptoActual, rutFuncionario);
        return mapearDepartamento(deptoEnRevision != null ? deptoEnRevision : deptoActual);
    }

    private Departamentos obtenerDepartamentoInicial(Departamentos departamentoRequest) {
        Long codigoDeptoActual = departamentoRequest.getDeptoInt();
        Departamentos depto = departamentosRepository.findByDeptoInt(codigoDeptoActual);

        if (depto == null) {
            throw new IllegalArgumentException("No se encontró el departamento con código: " + codigoDeptoActual);
        }

        return depto;
    }

    private Integer obtenerRutFuncionario(Funcionario funcionario) {
        String strRut = funcionario.getRut() != null ? funcionario.getRut().toString() : null;
        if (strRut == null || strRut.isBlank()) {
            return null;
        }

        return Integer.parseInt(strRut);
    }

    private Departamentos buscarDepartamentoConJefeValido(Departamentos deptoInicial, Integer rutFuncionario) {
        Departamentos deptoEnRevision = deptoInicial;

        while (deptoEnRevision != null) {
            if (tieneJefeValido(deptoEnRevision, rutFuncionario)) {
                return deptoEnRevision;
            }

            deptoEnRevision = obtenerDepartamentoSupervisor(deptoEnRevision);
        }

        return null;
    }

    private Departamentos obtenerDepartamentoSupervisor(Departamentos depto) {
        String codigoSupervisorStr = DepartamentoUtils.determinaDerivacion(depto.getDeptoInt());

        if (codigoSupervisorStr == null || codigoSupervisorStr.isBlank()) {
            return null;
        }

        try {
            Long codigoSupervisor = Long.parseLong(codigoSupervisorStr);
            return departamentosRepository.findByDeptoInt(codigoSupervisor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean tieneJefeValido(Departamentos depto, Integer rutFuncionario) {
        String rutJefeStr = depto.getRutJefe();

        if (rutJefeStr == null || rutJefeStr.trim().isEmpty()) {
            return false;
        }

        try {
            Integer rutJefe = Integer.parseInt(rutJefeStr.trim());
            return !rutJefe.equals(rutFuncionario);
        } catch (NumberFormatException _) {
            // Si el RUT del jefe no es un número válido, lo consideramos inválido
            return false;
        }
    }

    private Departamento mapearDepartamento(Departamentos depto) {
        Departamento destino = new Departamento();
        destino.setDepto(depto.getDeptoInt());
        destino.setDeptoSmc(depto.getDepto());
        destino.setNombre(depto.getNombreDepartamento());
        return destino;
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
                .toList();

        // Crear el DTO para cada solicitudh
        return solicitudes.stream().map(solicitud -> {
            List<Derivacion> derivacionesSolicitud = derivacionRepository.findBySolicitudId(solicitud.getId());
            List<Entrada> entradas = derivacionesSolicitud.stream()
                    .flatMap(derivacion -> entradaRepository.findByDerivacionId(derivacion.getId()).stream())
                    .toList();
            List<Salida> salidas = derivacionesSolicitud.stream()
                    .flatMap(derivacion -> salidaRepository.findByDerivacion_Id(derivacion.getId()).stream())
                    .toList();

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
        }).toList();
    }

    public List<Solicitud> servGetSolicitudesPorFuncionario(Integer rut) {

        return solicitudRespository.findByFuncionarioRut(rut);
    }

    public List<Solicitud> servGetSolicitudesPendientesPorFuncionario(Integer rut) {

        return solicitudRespository.findPendingAndNotRejectedByFuncionarioRut(rut);
    }

    public String getMail(Long depto) {
        Departamentos deptos = departamentosRepository.findByDeptoInt(depto);

        SmcPersona persona = smcService.getPersonaByRut(Integer.parseInt(deptos.getRutJefe()));

        return persona.getEmail();

    }

    public String getNombreJefe(Long depto) {
        Departamentos deptos = departamentosRepository.findByDeptoInt(depto);

        SmcPersona persona = smcService.getPersonaByRut(Integer.parseInt(deptos.getRutJefe()));

        return persona.getNombres();

    }

    @Transactional(readOnly = true)
    public List<Solicitud> obtenerSolicitudesPorDepartamentoYFechas(
            Long departamentoId, LocalDate fechaInicio, LocalDate fechaFin) {
        if (departamentoId == null || fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("El departamento y las fechas no deben ser nulos.");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        return solicitudRespository.findSolicitudesByDepartamentoOrigenAndFechas(
                departamentoId, fechaInicio, fechaFin);
    }

    public List<Subrogancia> buscaSubrogancias(Long depto, LocalDate fechaInicio, LocalDate fechaFin) {

        String deptoInt = depto.toString();

        return subroganciaRepository.findByDeptoAndDates(deptoInt, fechaInicio, fechaFin);

    }

    public Solicitud getSolicitud(Long id) {
        return solicitudRespository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la solicitud"));

    }
}