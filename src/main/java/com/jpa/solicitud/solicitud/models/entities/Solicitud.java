package com.jpa.solicitud.solicitud.models.entities;


import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaSolicitud;
    private Date fechaInicio;
    private Date fechaFin;
    private String glosa;

    public Solicitud() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    public Date getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Date getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getGlosa() {
        return glosa;
    }
    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public long calcularDiasHabiles(Date sqlStartDate, Date sqlEndDate) {
        LocalDate startDate = sqlStartDate.toLocalDate();
        LocalDate endDate = sqlEndDate.toLocalDate();

        // Si la fecha de inicio es después de la fecha de fin, no hay días hábiles
        if (startDate.isAfter(endDate)) {
            return 0;
        }

        long workingDays = 0;

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            // Añadir 1 al conteo de días hábiles si no es sábado ni domingo
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            // Moverse al siguiente día
            date = date.plusDays(1);
        }

        return workingDays;
    }
}
