package com.jpa.solicitud.solicitud.services;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.utils.FeriadoUtils;

@Service
public class UtilsService {

    public long calcularDiasHabiles(Date sqlStartDate, Date sqlEndDate) {

        return FeriadoUtils.calcularDiasHabiles(sqlStartDate, sqlEndDate);
    }

}
