package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import com.jpa.solicitud.solicitud.models.dto.SolicitudDerivacionDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudDto;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.TipoSolicitud;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.repositories.ITipoSolicitudRepository;

@Service
public class SolicitudService {

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

    
    private final RestTemplate restTemplate;

   
    public SolicitudService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public long calcularDiasHabiles(Date sqlStartDate, Date sqlEndDate) {
        LocalDate startDate = sqlStartDate.toLocalDate();
        LocalDate endDate = sqlEndDate.toLocalDate();

        if (startDate.isAfter(endDate)) {
            return 0;
        }

        long workingDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            date = date.plusDays(1);
        }

        return workingDays;
    }

    @Transactional
    public void procesarSolicitud(SolicitudDto solicitudDto) {
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

        // Crear y persistir la derivación
        Derivacion derivacion = new Derivacion();
        derivacion.setFechaDerivacion(solicitudDto.getFechaDer());
        derivacion.setDepartamentoCodigo(solicitudDto.getDepto());
        derivacion.setSolicitud(solicitud);
        derivacion.setEstado(estado);
        derivacion.setComentarios("Prueba de derivacion");
        derivacionRepository.save(derivacion);
    }

    public List<Solicitud> findAll() {
        return solicitudRespository.findAll();
    }

    public List<SolicitudDerivacionDto> obtenerSolicitudesPorDepartamento(Long departamentoCodigo) {
        List<Derivacion> derivaciones = derivacionRepository.findByDepartamentoCodigo(departamentoCodigo);
    
        return derivaciones.stream()
            .filter(derivacion -> !isJefe(derivacion.getSolicitud().getFuncionario().getRut()))
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
                dto.setDepartamentoCodigo(derivacion.getDepartamentoCodigo());
                dto.setComentarios(derivacion.getComentarios());
                dto.setRut(derivacion.getSolicitud().getFuncionario().getRut());
                dto.setNombreEstado(derivacion.getEstado().getNombre());
                dto.setNombreSolicitud(derivacion.getSolicitud().getTipoSolicitud().getNombre());
    
                return dto;
            }).collect(Collectors.toList());
    }

    public List<SolicitudDerivacionDto> obtenerSolicitudesPorRut(Integer rut) {
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
                    dto.setDepartamentoCodigo(derivacion.getDepartamentoCodigo());
                    dto.setComentarios(derivacion.getComentarios());
                    dto.setRut(derivacion.getSolicitud().getFuncionario().getRut());
                    dto.setNombreEstado(derivacion.getEstado().getNombre());
                    dto.setNombreSolicitud(derivacion.getSolicitud().getTipoSolicitud().getNombre());
    
                    return dto;
                })
                .collect(Collectors.toList());
    }
    

    public boolean isJefe(Integer rut) {
        String url = "http://localhost:8080/api/esjefe/" + rut;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return response.getBody() != null && response.getBody(); // Retornar true si es jefe
    }
}
