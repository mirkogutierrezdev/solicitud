package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcFeriado;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.FeriadoUtils;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class UtilsService {

    private final FeriadoUtils feriadoUtils;



    public UtilsService(FeriadoUtils feriadoUtils) {
        this.feriadoUtils = feriadoUtils;

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


}
