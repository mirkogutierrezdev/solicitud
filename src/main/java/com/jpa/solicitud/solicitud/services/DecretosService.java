package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.dto.DecretosDto;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.models.entities.Decretos;
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

        decreto.setNroDecreto(decretoDTO.getNroDecreto());
        decreto.setFechaDecreto(fechaDecreto);
        
        


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

        return savedDecreto; // Devolver el decreto guardado si es necesario
    }

    public Decretos getDecretos(Long id){
        return decretoRepository.findDecretosWithAprobacion(id);
    }
}
