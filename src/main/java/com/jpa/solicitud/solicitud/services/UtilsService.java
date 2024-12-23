package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcFeriado;
import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.FeriadoUtils;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class UtilsService {

    private final FeriadoUtils feriadoUtils;

    private final IDepartamentosRepository departamentosRepository;

    private final SmcService smcService;

    public UtilsService(FeriadoUtils feriadoUtils, IDepartamentosRepository departamentoRepository,
            SmcService smcService) {
        this.feriadoUtils = feriadoUtils;
        this.departamentosRepository = departamentoRepository;
        this.smcService = smcService;

    }

    public long getWorkDays(Date sqlStartDate, Date sqlEndDate) {

        return feriadoUtils.calcularDiasHabiles(sqlStartDate, sqlEndDate);
    }

    public String determinaDerivacion(Long depto) {
        return DepartamentoUtils.determinaDerivacion(depto);
    }

    public String buildName(String nombre, String apellidoPaterno, String apellidoMaterno) {

        return StringUtils.buildName(nombre, apellidoPaterno, apellidoMaterno);
    }

    public List<SmcFeriado> getFeriados(Date fechaInicio, Date fechaTermino) {
        return feriadoUtils.getFeriados(fechaInicio, fechaTermino);
    }

    public String jefeDestino(Long depto) {

        Departamentos deptos = departamentosRepository.findByDepto(depto);

        String deptoDestino = determinaDerivacion(depto);

        if (deptos.getRutJefe().isEmpty()) {

            String deptoDestino2 = determinaDerivacion(Long.parseLong(deptoDestino));

            Departamentos deptos2 = departamentosRepository.findByDeptoInt(Long.parseLong(deptoDestino2));

            Integer rutDepto2 = Integer.parseInt(deptos2.getRutJefe());

            SmcPersona persona = smcService.getFuncionarioByRut(rutDepto2);

            return StringUtils.buildName(persona.getNombres(), persona.getApellidopaterno(),
                    persona.getApellidomaterno());
        }

        SmcPersona otraPersona = smcService.getPersonaByRut(Integer.parseInt(deptos.getRutJefe()));

        return StringUtils.buildName(otraPersona.getNombres(), otraPersona.getApellidopaterno(),
                otraPersona.getApellidomaterno());

    }

}
