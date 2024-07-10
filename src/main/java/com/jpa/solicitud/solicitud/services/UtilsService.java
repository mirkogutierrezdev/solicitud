package com.jpa.solicitud.solicitud.services;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.FeriadoUtils;

@Service
public class UtilsService {

    public long getWorkDays(Date sqlStartDate, Date sqlEndDate) {

        return FeriadoUtils.calcularDiasHabiles(sqlStartDate, sqlEndDate);
    }

    public String determinaDerivacion(Long depto){
        return DepartamentoUtils.determinaDerivacion(depto);
    }



}
