package com.jpa.solicitud.solicitud.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.EntradaDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudEntradaDto;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Entrada;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEntradaRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EntradaService {

    @Autowired
    private IEntradaRepository entradaRepository;

    @Autowired
    private IDerivacionRepository derivacionRepository;

    @Autowired
    private IFuncionarioRespository funcionarioRespository;

    @Autowired
    private SmcService smcService;

    public Entrada saveEntrada(EntradaDto entradaDto) {
        Derivacion derivacion = derivacionRepository.findDerivacionByIdAndFuncionario(
                entradaDto.getDerivacionId(), entradaDto.getFuncionarioId());

        if (derivacion == null) {
            throw new IllegalArgumentException(
                    "La derivación proporcionada no existe o no está asociada al funcionario");
        }

        
        Funcionario funcionario = new Funcionario();
        funcionario = funcionarioRespository.save(funcionario);
        funcionario.setRut(entradaDto.getRut());

        Entrada entrada = new Entrada();
        entrada.setFechaEntrada(entradaDto.getFechaEntrada());
        entrada.setDerivacion(derivacion);
        entrada.setFuncionario(funcionario);

        return entradaRepository.save(entrada);
    }

    public List<Entrada> findAll() {

        return entradaRepository.findAll();

    }

    public Entrada findById(Long id) {
        return entradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con id: " + id));
    }

    public List<SolicitudEntradaDto> findEntradaByDepto(Long depto){

          List<Entrada> entradas = entradaRepository.findEntradaByDepto(depto);

          return entradas.stream()
                  .map(entrada ->{

                    SolicitudEntradaDto dto = new SolicitudEntradaDto();

                    SmcPersona persona = smcService.getPersonaByRut(entrada.getFuncionario().getRut());

                    dto.setNombre(persona.getNombres());
                    dto.setDepartamentoCodigo(entrada.getDerivacion().getDepartamento().getDepto());
                    dto.setFechaEntrada(entrada.getFechaEntrada());
                    dto.setFechaSolicitud(entrada.getDerivacion().getSolicitud().getFechaSolicitud());
                    dto.setSolicitudId(entrada.getDerivacion().getSolicitud().getId());
                    dto.setRut(entrada.getFuncionario().getRut());
                    dto.setFechaInicio(entrada.getDerivacion().getSolicitud().getFechaInicio());
                    dto.setFechaFin(entrada.getDerivacion().getSolicitud().getFechaFin());
                    dto.setTipoSolicitudId(entrada.getDerivacion().getSolicitud().getId());
                    dto.setNombreSolicitud(entrada.getDerivacion().getSolicitud().getTipoSolicitud().getNombre());
                    dto.setEstadoId(entrada.getDerivacion().getEstado().getId());
                    dto.setNombreEstado(entrada.getDerivacion().getSolicitud().getEstado().getNombre());
                    dto.setFuncionarioId(entrada.getFuncionario().getId());
                    dto.setNombreDepartamento(entrada.getDerivacion().getDepartamento().getNombre());

                    System.out.println("id " + entrada.getDerivacion().getSolicitud().getId());




                    return dto;


                  }).collect(Collectors.toList());






    }
}
