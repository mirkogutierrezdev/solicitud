package com.jpa.solicitud.solicitud.utils;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class FeriadoUtils {

    public static long calcularDiasHabiles(Date sqlStartDate, Date sqlEndDate) {
        LocalDate startDate = sqlStartDate.toLocalDate();
        LocalDate endDate = sqlEndDate.toLocalDate();

        if (startDate.isAfter(endDate)) {
            return 0;
        }

        long workingDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            date = date.plusDays(1);
        }

        return workingDays;
    }

}
