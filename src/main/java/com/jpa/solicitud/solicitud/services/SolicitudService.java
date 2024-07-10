package com.jpa.solicitud.solicitud.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.SolicitudDerivacionDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudDto;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.TipoSolicitud;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.repositories.ITipoSolicitudRepository;

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
    private SmcService smcService;

    // *RestTemplate para la api a smc */
    public SolicitudService(RestTemplate restTemplate) {
    }

    @Transactional
    public Solicitud saveSolicitud(SolicitudDto solicitudDto) {
        // Crear y persistir el funcionario
        Funcionario funcionario = new Funcionario();
        funcionario.setRut(solicitudDto.getRut());
        funcionario = funcionarioRespository.save(funcionario);

        // Obtener el tipo de solicitud por nombre y lanzar excepción si no se encuentra
        Long idSol = tipoSolicitudRepository.findIdByNombre(solicitudDto.getTipoSolicitud());
        if (idSol == null) {
            throw new IllegalArgumentException("Tipo de solicitud no encontrado");
        }
        TipoSolicitud tipoSol = tipoSolicitudRepository.findById(idSol)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de solicitud no encontrado"));

        // Obtener el estado por nombre y lanzar excepción si no se encuentra
        Long idEstado = estadoRepository.findIdByNombre(solicitudDto.getEstado());
        if (idEstado == null) {
            throw new IllegalArgumentException("Estado no encontrado");
        }
        Estado estado = estadoRepository.findById(idEstado)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));

        // Crear y persistir la solicitud
        Solicitud solicitud = new Solicitud();
        solicitud.setFuncionario(funcionario);
        solicitud.setTipoSolicitud(tipoSol);
        solicitud.setFechaSolicitud(solicitudDto.getFechaSol());
        solicitud.setFechaInicio(solicitudDto.getFechaInicio());
        solicitud.setFechaFin(solicitudDto.getFechaFin());
        solicitud.setEstado(estado);
        solicitud = solicitudRespository.save(solicitud);

        Departamento departamento = new Departamento();
        departamento.setDepto(solicitudDto.getDepto());
        departamento.setNombre(solicitudDto.getNombre_departamento());
        departamento = departamentoRepository.save(departamento);

        // Crear y persistir la derivación
        Derivacion derivacion = new Derivacion();
        derivacion.setFechaDerivacion(solicitudDto.getFechaDer());
        derivacion.setLeida(false);
        derivacion.setDepartamento(departamento);
        derivacion.setSolicitud(solicitud);
        derivacion.setEstado(estado);
        derivacion.setComentarios("Prueba de derivacion");
        derivacionRepository.save(derivacion);

        return solicitud;
    }

    public List<Solicitud> findAll() {
        return solicitudRespository.findAll();
    }

    public List<SolicitudDerivacionDto> getSolicitudesByDepto(Long departamentoCodigo) {
        List<Derivacion> derivaciones = derivacionRepository.findByDepartamentoDepto(departamentoCodigo);

        StringBuilder nombres = new StringBuilder();

        return derivaciones.stream()
                 .filter(derivacion -> !smcService.isJefe(derivacion.getSolicitud().getFuncionario().getRut()))
                
                .map(derivacion -> {
                    SolicitudDerivacionDto dto = new SolicitudDerivacionDto();
                    SmcPersona persona = smcService
                            .getPersonaByRut(derivacion.getSolicitud().getFuncionario().getRut());
                    dto.setSolicitudId(derivacion.getSolicitud().getId());
                    dto.setFuncionarioId(derivacion.getSolicitud().getFuncionario().getId());
                    dto.setFechaSolicitud(derivacion.getSolicitud().getFechaSolicitud());
                    dto.setFechaInicio(derivacion.getSolicitud().getFechaInicio());
                    dto.setFechaFin(derivacion.getSolicitud().getFechaFin());
                    dto.setTipoSolicitudId(derivacion.getSolicitud().getTipoSolicitud().getId());
                    dto.setEstadoId(derivacion.getSolicitud().getEstado().getId());
                    dto.setDerivacionId(derivacion.getId());
                    dto.setFechaDerivacion(derivacion.getFechaDerivacion());
                    dto.setDepartamentoCodigo(derivacion.getDepartamento().getDepto());
                    dto.setLeida(derivacion.getLeida());
                    dto.setNombreDepartamento(derivacion.getDepartamento().getNombre());
                    dto.setComentarios(derivacion.getComentarios());
                    dto.setRut(derivacion.getSolicitud().getFuncionario().getRut());
                    dto.setNombreEstado(derivacion.getEstado().getNombre());
                    dto.setNombreSolicitud(derivacion.getSolicitud().getTipoSolicitud().getNombre());
                    nombres.append(persona.getNombres()).append(" ").append(persona.getApellidopaterno());
                    dto.setNombre(nombres.toString());
                    nombres.setLength(0);

                    return dto;
                }).collect(Collectors.toList());
    }

    public List<SolicitudDerivacionDto> getSolicitudesByRut(Integer rut) {

        List<Derivacion> derivaciones = derivacionRepository.findByFuncionarioRut(rut);

        return derivaciones.stream()
                .map(derivacion -> {
                    SolicitudDerivacionDto dto = new SolicitudDerivacionDto();

                    dto.setSolicitudId(derivacion.getSolicitud().getId());
                    dto.setFuncionarioId(derivacion.getSolicitud().getFuncionario().getId());
                    dto.setFechaSolicitud(derivacion.getSolicitud().getFechaSolicitud());
                    dto.setFechaInicio(derivacion.getSolicitud().getFechaInicio());
                    dto.setFechaFin(derivacion.getSolicitud().getFechaFin());
                    dto.setTipoSolicitudId(derivacion.getSolicitud().getTipoSolicitud().getId());
                    dto.setEstadoId(derivacion.getSolicitud().getEstado().getId());
                    dto.setDerivacionId(derivacion.getId());
                    dto.setFechaDerivacion(derivacion.getFechaDerivacion());
                    dto.setDepartamentoCodigo(derivacion.getDepartamento().getDepto());
                    dto.setComentarios(derivacion.getComentarios());
                    dto.setRut(derivacion.getSolicitud().getFuncionario().getRut());
                    dto.setNombreEstado(derivacion.getEstado().getNombre());
                    dto.setNombreSolicitud(derivacion.getSolicitud().getTipoSolicitud().getNombre());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    
}
