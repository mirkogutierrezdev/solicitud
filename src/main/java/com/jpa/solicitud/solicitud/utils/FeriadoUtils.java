package com.jpa.solicitud.solicitud.utils;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcFeriado;
import com.jpa.solicitud.solicitud.services.SmcService;

@Service
public class FeriadoUtils {

    @Autowired
    private SmcService smcService;

    public long calcularDiasHabiles(Date sqlStartDate, Date sqlEndDate) {
        LocalDate startDate = sqlStartDate.toLocalDate();
        LocalDate endDate = sqlEndDate.toLocalDate();

        System.out.println("startDate: utils " + startDate);
        System.out.println("endDate: utils " + endDate);

        if (startDate.isAfter(endDate)) {
            return 0;
        }

        // Llamar a getFeriados para obtener la lista de feriados
        List<SmcFeriado> feriados = getFeriados(sqlStartDate, sqlEndDate);

        // Imprimir los feriados obtenidos
        System.out.println("Feriados: " + feriados);

        // Convertir la lista de feriados a una lista de LocalDate
        List<LocalDate> feriadoDates = feriados.stream()
                .map(feriado -> feriado.getFeriado().toLocalDate())
                .collect(Collectors.toList());

        long workingDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY &&
                date.getDayOfWeek() != DayOfWeek.SUNDAY &&
                !feriadoDates.contains(date)) {
                workingDays++;
            }
            date = date.plusDays(1);
        }

        return workingDays;
    }

    public List<SmcFeriado> getFeriados(Date fechaInicio, Date fechaTermino) {
        return smcService.getFeriados(fechaInicio, fechaTermino);
    }

}
