package com.jpa.solicitud.solicitud.services;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.FeriadoUtils;

@Service
public class UtilsService {

    public long calcularDiasHabiles(Date sqlStartDate, Date sqlEndDate) {

        return FeriadoUtils.calcularDiasHabiles(sqlStartDate, sqlEndDate);
    }

    public String determinaDerivacion(Long depto) {
        String codigo = depto.toString();
    
        if (DepartamentoUtils.esDepartamento(codigo)) {
            return codigo.substring(0,2).concat("000000");
        }
    
        if (DepartamentoUtils.esSeccion(codigo)) {
            return codigo.substring(0,4).concat("0000");
        }
    
        if (DepartamentoUtils.esOficina(codigo)) {
            return codigo.substring(0,6).concat("00");
        }
    
        return codigo;
    }

}
