package com.jpa.solicitud.solicitud.services;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.FeriadoUtils;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class UtilsService {

    @Autowired
    private FeriadoUtils feriadoUtils;

    public long getWorkDays(Date sqlStartDate, Date sqlEndDate) {

        return feriadoUtils.calcularDiasHabiles(sqlStartDate, sqlEndDate);
    }

    public String determinaDerivacion(Long depto){
        return DepartamentoUtils.determinaDerivacion(depto);
    }

    public String buildName(String nombre, String apellidoPaterno, String apellidoMaterno){

        return StringUtils.buildName(nombre, apellidoPaterno, apellidoMaterno);
    }


}
