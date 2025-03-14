package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.DerivacionDto;
import com.jpa.solicitud.solicitud.models.dto.VDerivacionDto;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.Visacion;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.repositories.IVisacionRepository;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.StringUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class DerivacionService {

    private final IDerivacionRepository derivacionRepository;

    private final ISolicitudRespository solicitudRespository;

    private final SmcService smcService;

    private final IDepartamentoRepository departamentoRepository;

    private final IEstadoRepository estadoRepository;

    private final SalidaService salidaService;

    private final IFuncionarioRespository funcionarioRespository;

    private final IDepartamentosRepository departamentosRepository;

    private final IVisacionRepository visacionRepository;

    private final ApiService apiService;

    public DerivacionService(IDerivacionRepository derivacionRepository, ISolicitudRespository solicitudRespository,
            SmcService smcService, IDepartamentoRepository departamentoRepository, IEstadoRepository estadoRepository,
            SalidaService salidaService, IFuncionarioRespository funcionarioRespository,
            IDepartamentosRepository departamentosRepository,
            IVisacionRepository visacionRepository,
            ApiService apiService) {
        this.derivacionRepository = derivacionRepository;
        this.solicitudRespository = solicitudRespository;
        this.smcService = smcService;
        this.departamentoRepository = departamentoRepository;
        this.estadoRepository = estadoRepository;
        this.salidaService = salidaService;
        this.funcionarioRespository = funcionarioRespository;
        this.departamentosRepository = departamentosRepository;
        this.visacionRepository = visacionRepository;
        this.apiService = apiService;
    }

    public List<Derivacion> findBySolicitudId(Long id) {
        return derivacionRepository.findBySolicitudId(id);
    }

    @Transactional
    public Derivacion saveDerivacion(DerivacionDto derivacionDto) {

        Long idSolicitud = derivacionDto.getSolicitudId();
        String estado = derivacionDto.getEstado();
        Date fechaDerivacion = Date.valueOf(LocalDate.now());

        // Crear y configurar la entidad Derivacion
        Derivacion derivacion = new Derivacion();

        // Buscar la solicitud relacionada
        Optional<Solicitud> solicitudOpt = solicitudRespository.findById(idSolicitud);
        if (!solicitudOpt.isPresent()) {
            throw new EntityNotFoundException("Solicitud no encontrada con ID: " + idSolicitud);
        }

        // Obtiene la solicitud si está presente, sino lanza una excepción
        Solicitud solicitud = solicitudOpt.orElseThrow(() -> new IllegalStateException("Solicitud no encontrada"));

        // Verifica si tiene derivaciones antes de buscar la última
        Derivacion ultimaDerivacion = (solicitud.getDerivaciones() == null || solicitud.getDerivaciones().isEmpty())
                ? null
                : solicitud.getDerivaciones()
                        .stream()
                        .max(Comparator.comparing(Derivacion::getFechaDerivacion))
                        .orElse(null);

        if (ultimaDerivacion == null || ultimaDerivacion.getDepartamento() == null) {
            throw new IllegalStateException("No hay derivaciones o el departamento es nulo");
        }

        // Busca el departamento actual en SMC
        Departamentos deptoActualSmc = departamentosRepository
                .findByDepto(ultimaDerivacion.getDepartamento().getDeptoSmc());
        if (deptoActualSmc == null) {
            throw new IllegalStateException("Departamento actual no encontrado en SMC");
        }

        Long deptoInt = deptoActualSmc.getDeptoInt();

        // Determina la derivación
        String strDeptoDestino = DepartamentoUtils.determinaDerivacion(deptoInt);
        Long intDepto;
        try {
            intDepto = Long.parseLong(strDeptoDestino);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Error al convertir departamento destino a número", e);
        }

        // Busca el departamento de destino
        Departamentos deptoDestinoSmc = departamentosRepository.findByDeptoInt(intDepto);
        if (deptoDestinoSmc == null) {
            throw new IllegalStateException("Departamento de destino no encontrado en SMC");
        }

        // Crear y configurar el departamento de origen
        Departamento deptoOrigen = new Departamento();
        deptoOrigen.setDepto(deptoActualSmc.getDeptoInt());
        deptoOrigen.setDeptoSmc(deptoActualSmc.getDepto());
        deptoOrigen.setNombre(deptoActualSmc.getNombreDepartamento());

        // Guardar el departamento de origen
        deptoOrigen = departamentoRepository.save(deptoOrigen);

        // Crear y configurar el departamento de destino
        Departamento deptoDestino = new Departamento();
        deptoDestino.setDepto(deptoDestinoSmc.getDeptoInt());
        deptoDestino.setDeptoSmc(deptoDestinoSmc.getDepto());
        deptoDestino.setNombre(deptoDestinoSmc.getNombreDepartamento());

        // Guardar el departamento de destino
        deptoDestino = departamentoRepository.save(deptoDestino);

        // Crear y configurar la entidad Estado
        Long codEstado = estadoRepository.findIdByNombre(estado);
        Estado estadoSol = new Estado();
        estadoSol.setId(codEstado);
        estadoSol.setNombre(estado);

        // Crear y configurar la entidad Funcionario
        SmcPersona persona = smcService.getPersonaByRut(derivacionDto.getRut());
        Funcionario funcionario = new Funcionario();
        funcionario.setRut(persona.getRut());
        funcionario.setNombre(
                StringUtils.buildName(persona.getNombres(), persona.getApellidopaterno(),
                        persona.getApellidomaterno()));
        funcionario = funcionarioRespository.save(funcionario);

        String rutJefe = deptoActualSmc.getRutJefe();

        SmcPersona personaJefe = smcService.getPersonaByRut(Integer.parseInt(rutJefe));

        Funcionario jefeDepto = new Funcionario();
        jefeDepto.setRut(personaJefe.getRut());
        jefeDepto.setNombre(StringUtils.buildName(personaJefe.getNombres(), personaJefe.getApellidopaterno(),
                personaJefe.getApellidomaterno()));

        jefeDepto = funcionarioRespository.save(jefeDepto);

        Visacion visacion = new Visacion();
        visacion.setSolicitud(solicitud);
        visacion.setFuncionario(jefeDepto);
        visacion.setFechaVisacion(LocalDate.now());
        visacion.setTransaccion(LocalDateTime.now());

        visacionRepository.save(visacion);

        // Configurar la entidad Derivacion con las entidades relacionadas
        derivacion.setSolicitud(solicitud);
        derivacion.setDepartamento(deptoDestino); // Departamento destino
        derivacion.setDepartamentoOrigen(deptoOrigen); // Departamento origen
        derivacion.setFechaDerivacion(fechaDerivacion);
        derivacion.setLeida(false);
        derivacion.setFuncionario(funcionario);

        // Guardar la derivacion
        derivacion = derivacionRepository.save(derivacion);

        // Crear y configurar la entidad Salida
        salidaService.saveSalida(derivacion, funcionario);

        // Enviar correo al jefe del departamento destino
        String mail = getMail(deptoDestino.getDepto());
        String nombreJefe = getNombreJefe(deptoDestino.getDepto());
        apiService.sendEmail("1", nombreJefe, solicitud.getTipoSolicitud().getNombre(), mail);

        return derivacion;
    }

    @Transactional
    public Derivacion saveDerivacion(Departamento departamento, Solicitud solicitud, Funcionario funcionario) {

        Date fechaDerivacion = Date.valueOf(LocalDate.now());
        Derivacion derivacion = new Derivacion();
        derivacion.setDepartamento(departamento);
        derivacion.setSolicitud(solicitud);
        derivacion.setFuncionario(funcionario);
        derivacion.setFechaDerivacion(fechaDerivacion);

        derivacion.setLeida(false);
        return derivacionRepository.save(derivacion);
    }

    public Optional<Derivacion> findDerivacionById(Long id) {
        Optional<Derivacion> derivacion = derivacionRepository.findById(id);
        return Optional.ofNullable(derivacion.orElseThrow(() -> new RuntimeException("Derivacion not found")));
    }

    public List<VDerivacionDto> findDerivacionesBySolicitud(Long solicitudId) {
        return derivacionRepository.findDerivacionesBySolicitud(solicitudId);
    }

    public List<Derivacion> saveDerivaciones(List<DerivacionDto> derivacionesDto) {
        return derivacionesDto.stream().map(this::saveDerivacion).toList();
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

}
