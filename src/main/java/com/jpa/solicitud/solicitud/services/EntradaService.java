package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.EntradaDto;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Entrada;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEntradaRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.utils.StringUtils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EntradaService {

    private final IEntradaRepository entradaRepository;

    private final IDerivacionRepository derivacionRepository;

    private final IFuncionarioRespository funcionarioRespository;

    private final SmcService smcService;

    public EntradaService(IEntradaRepository entradaRepository, IDerivacionRepository derivacionRepository,
            IFuncionarioRespository funcionarioRespository, SmcService smcService) {
        this.entradaRepository = entradaRepository;
        this.derivacionRepository = derivacionRepository;
        this.funcionarioRespository = funcionarioRespository;
        this.smcService = smcService;
    }

    public Entrada saveEntrada(EntradaDto entradaDto) {

        Date getFechaEntrada = Date.valueOf(LocalDate.now());
    
        // Encuentra todas las derivaciones asociadas a la solicitud
        List<Derivacion> derivaciones = derivacionRepository.findBySolicitudId(entradaDto.getSolicitudId());
    
        if (derivaciones.isEmpty()) {
            throw new IllegalArgumentException("No hay derivaciones asociadas a la solicitud proporcionada.");
        }
    
        // Encuentra la última derivación basada en el id
        Derivacion ultimaDerivacion = derivaciones.stream()
                .max(Comparator.comparing(Derivacion::getId))
                .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar la última derivación."));
    
        // Verifica si ya existe una entrada para esta derivación
        List<Entrada> entradaCheck = entradaRepository.findByDerivacionId(ultimaDerivacion.getId());
        if (!entradaCheck.isEmpty()) {
            throw new IllegalStateException("Ya existe una entrada para esta derivación.");
        }
    
        // Obtiene nombre de la base de datos Personas de Smc
        SmcPersona persona = smcService.getPersonaByRut(entradaDto.getRut());
    
        // Crea y persiste Objeto Funcionario con los datos traidos de Smc
        Funcionario funcionario = new Funcionario();
        funcionario.setRut(persona.getRut());
    
        // Utiliza metodo estatico Buildname para concatenar el nombre
        funcionario.setNombre(
                StringUtils.buildName(persona.getNombres(),
                        persona.getApellidopaterno(),
                        persona.getApellidomaterno()));
    
        funcionario = funcionarioRespository.save(funcionario);
    
        // Crea y persiste Entrada
        Entrada entrada = new Entrada();
        entrada.setFechaEntrada(getFechaEntrada);
        entrada.setDerivacion(ultimaDerivacion);
        entrada.setFuncionario(funcionario);
    
        ultimaDerivacion.setLeida(true);
    
        return entradaRepository.save(entrada);
    }
    

    public List<Entrada> findAll() {

        return entradaRepository.findAll();
    }

    public Entrada findById(Long id) {
        return entradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con id: " + id));
    }

    public List<Entrada> saveEntradas(List<EntradaDto> entradasDto) {
        return entradasDto.stream().map(this::saveEntrada).collect(Collectors.toList());
    }
}
