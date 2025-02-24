package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcContrato;
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

    private final SmcService smcService;

    public DecretosService(IDecretosRepository decretosRepository, IAprobacionRepository aprobacionRepository,
            SmcService smcService) {
        this.decretoRepository = decretosRepository;
        this.aprobacionRepository = aprobacionRepository;
        this.smcService = smcService;
    }

    @Transactional
    public Decretos crearDecreto(DecretosDto decretoDTO) {
        // 1. Crear un nuevo Decreto
        Decretos decreto = new Decretos();
        LocalDateTime fechaDecreto = LocalDateTime.now();
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
        ZoneId zoneId = ZoneId.of("America/Santiago"); // Define la zona horaria deseada
        ZoneId utcZone = ZoneId.of("UTC");

        // Convertir fechas de entrada a UTC para asegurar comparación correcta
        ZonedDateTime fechaInicioUTC = fechaInicio.atStartOfDay(zoneId).withZoneSameInstant(utcZone);
        ZonedDateTime fechaFinUTC = fechaFin.atTime(LocalTime.MAX).atZone(zoneId).withZoneSameInstant(utcZone);

        List<Decretos> decretos = decretoRepository.findAll();

        return decretos.stream()
                .filter(decreto -> {
                    ZonedDateTime fechaCreacionUTC = decreto.getFechaCreacion().atZone(zoneId)
                            .withZoneSameInstant(utcZone);
                    return !fechaCreacionUTC.toLocalDate().isBefore(fechaInicioUTC.toLocalDate()) &&
                            !fechaCreacionUTC.toLocalDate().isAfter(fechaFinUTC.toLocalDate());
                })
                .flatMap(decreto -> decreto.getAprobaciones().stream()
                        .map(aprob -> {
                            DecretosResponse decretoResponse = new DecretosResponse();

                            decretoResponse.setId(decreto.getId());
                            decretoResponse.setFechaCreacion(decreto.getFechaCreacion().atZone(zoneId)
                                    .withZoneSameInstant(utcZone).toLocalDateTime());

                            Solicitud solicitud = aprob.getSolicitud();

                            decretoResponse.setIdSolicitud(solicitud.getId());
                            decretoResponse.setRut(solicitud.getFuncionario().getRut());
                            decretoResponse.setNombre(solicitud.getFuncionario().getNombre());
                            decretoResponse.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
                            decretoResponse.setFechaSolicitud(solicitud.getFechaSolicitud());
                            decretoResponse.setFechaInicio(solicitud.getFechaInicio().atZone(zoneId).toLocalDateTime());
                            decretoResponse.setFechaFin(solicitud.getFechaFin().atZone(zoneId).toLocalDateTime());
                            decretoResponse.setDuracion(solicitud.getDuracion());
                            decretoResponse.setUrlPdf(aprob.getUrlPdf());
                            
                            SmcContrato contrato = smcService.getContratoSmc(aprob.getSolicitud().getFuncionario().getRut());

                            decretoResponse.setTipoContrato(contrato.getNombrecontrato());

                            List<Derivacion> derivacion = solicitud.getDerivaciones();
                            if (!derivacion.isEmpty()) {
                                decretoResponse.setDepto(derivacion.get(0).getDepartamentoOrigen().getNombre());
                            }

                            decretoResponse.setAprobadoPor(aprob.getFuncionario().getNombre());

                            return decretoResponse;
                        }))
                .toList();
    }

}
