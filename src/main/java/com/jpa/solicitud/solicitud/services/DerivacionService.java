package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.DerivacionDto;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Salida;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISalidaRepository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.StringUtils;

import jakarta.persistence.EntityNotFoundException;
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

    @Autowired
    private ISalidaRepository salidaRepository;

    @Autowired
    private IFuncionarioRespository funcionarioRespository;

    @Autowired
    private IDepartamentosRepository departamentosRepository;

    public List<Derivacion> findBySolicitudId(Long id) {
        return derivacionRepository.findBySolicitudId(id);
    }

    @Transactional
    public Derivacion saveDerivacion(DerivacionDto derivacionDto) {
        Long depto = derivacionDto.getDepto();
        Long idSolicitud = derivacionDto.getIdSolicitud();
        String estado = derivacionDto.getEstado();
        Date fechaDerivacion = derivacionDto.getFechaDerivacion();

        // Crear y configurar la entidad Derivacion
        Derivacion derivacion = new Derivacion();

        // Buscar la solicitud relacionada
        Optional<Solicitud> solicitudOpt = solicitudRespository.findById(idSolicitud);
        if (!solicitudOpt.isPresent()) {
            throw new EntityNotFoundException("Solicitud no encontrada con ID: " + idSolicitud);
        }
        Solicitud solicitud = solicitudOpt.get();

        // Busca codigo interno en tabla de conversión
        Departamentos deptoActualSmc = departamentosRepository.findByDepto(depto);
        Long deptoInt = deptoActualSmc.getDeptoInt();

        // Determinar el departamento de destino

        String strDeptoDestino = DepartamentoUtils.determinaDerivacion(deptoInt);
        Long intDepto = Long.parseLong(strDeptoDestino);

        Departamentos deptoDestinoSmc = departamentosRepository.findByDeptoInt(intDepto);

        // Crear y configurar la entidad Departamento

        Departamento deptoDestino = new Departamento();

        deptoDestino.setDepto(deptoDestinoSmc.getDeptoInt());
        deptoDestino.setDeptoSmc(deptoDestinoSmc.getDepto());
        deptoDestino.setNombre(deptoDestinoSmc.getNombre_departamento());

        // Guardar el departamento
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

        // Configurar la entidad Derivacion con las entidades relacionadas
        derivacion.setSolicitud(solicitud);
        derivacion.setDepartamento(deptoDestino);
        derivacion.setFechaDerivacion(fechaDerivacion);
        derivacion.setComentarios("Prueba de derivacion");
        derivacion.setLeida(false);
        derivacion.setFuncionario(funcionario);

        // Guardar la derivacion
        derivacion = derivacionRepository.save(derivacion);

        // Crear y configurar la entidad Salida
        Salida salida = new Salida();
        salida.setDerivacion(derivacion);
        salida.setFuncionario(funcionario);
        salida.setFechaSalida(derivacionDto.getFechaDerivacion());
        salida = salidaRepository.save(salida);

        return derivacion;
    }

    public Optional<Derivacion> findDerivacionById(Long id) {
        Optional<Derivacion> derivacion = derivacionRepository.findById(id);
        return Optional.ofNullable(derivacion.orElseThrow(() -> new RuntimeException("Derivacion not found")));
    }



}
