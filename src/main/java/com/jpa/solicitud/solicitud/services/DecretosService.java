package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.dto.DecretosDto;
import com.jpa.solicitud.solicitud.models.dto.DecretosResponse;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.models.entities.Decretos;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.repositories.IAprobacionRepository;
import com.jpa.solicitud.solicitud.repositories.IDecretosRepository;

import jakarta.transaction.Transactional;

@Service
public class DecretosService {

    private final IDecretosRepository decretoRepository;

    private final IAprobacionRepository aprobacionRepository;

    public DecretosService(IDecretosRepository decretosRepository, IAprobacionRepository aprobacionRepository) {
        this.decretoRepository = decretosRepository;
        this.aprobacionRepository = aprobacionRepository;
    }

    @Transactional
    public Decretos crearDecreto(DecretosDto decretoDTO) {
        // 1. Crear un nuevo Decreto
        Decretos decreto = new Decretos();
        LocalDate fechaDecreto = LocalDate.now();
        decreto.setFechaCreacion(fechaDecreto);

        // 2. Obtener las aprobaciones por sus IDs
        List<Aprobacion> aprobaciones = aprobacionRepository.findAllById(decretoDTO.getAprobacionesIds());

        // 3. Asociar las aprobaciones al decreto
        for (Aprobacion aprobacion : aprobaciones) {
            aprobacion.setDecreto(decreto); // Relacionar la aprobación con el decreto
        }

        // 4. Guardar el decreto (esto también guardará las aprobaciones si están
        // configuradas con cascade)
        Decretos savedDecreto = decretoRepository.save(decreto);

        // 5. Guardar las aprobaciones actualizadas
        aprobacionRepository.saveAll(aprobaciones);

        // 6. Generar el documento Word después de guardar el decreto

        return savedDecreto; // Devolver el decreto guardado si es necesario
    }

    public List<DecretosResponse> findAll(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Decretos> decretos = decretoRepository.findAll();
    
        return decretos.stream()
                // Filtramos por fechaCreacion dentro del rango especificado
                .filter(decreto -> !decreto.getFechaCreacion().isBefore(fechaInicio) &&
                                   !decreto.getFechaCreacion().isAfter(fechaFin))
                .flatMap(decreto -> decreto.getAprobaciones().stream()
                        .map(aprob -> {
                            DecretosResponse decretoResponse = new DecretosResponse();
    
                            // Detalles básicos del decreto
                            decretoResponse.setId(decreto.getId());
                            decretoResponse.setFechaCreacion(decreto.getFechaCreacion());
    
                            // Detalles de la solicitud asociada a la aprobación
                            Solicitud solicitud = aprob.getSolicitud();
    
                            // Asignamos los valores de la solicitud a la respuesta
                            decretoResponse.setIdSolicitud(solicitud.getId());
                            decretoResponse.setRut(solicitud.getFuncionario().getRut());
                            decretoResponse.setNombre(solicitud.getFuncionario().getNombre());
                            decretoResponse.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
                            decretoResponse.setFechaSolicitud(solicitud.getFechaSolicitud());
                            decretoResponse.setFechaInicio(solicitud.getFechaInicio().toLocalDate());
                            decretoResponse.setFechaFin(solicitud.getFechaFin().toLocalDate());
                            decretoResponse.setDuracion(solicitud.getDuracion());
                            decretoResponse.setUrlPdf(aprob.getUrlPdf());
    
                            List<Derivacion> derivacion = solicitud.getDerivaciones();
                            if (!derivacion.isEmpty()) {
                                decretoResponse.setDepto(derivacion.get(0).getDepartamentoOrigen().getNombre());
                            }
    
                            // Detalles de la aprobación
                            decretoResponse.setAprobadoPor(aprob.getFuncionario().getNombre());
    
                            return decretoResponse;
                        })
                )
                .toList();
    }
    
    
}
