package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Salida;
import com.jpa.solicitud.solicitud.repositories.ISalidaRepository;

import jakarta.transaction.Transactional;

@Service
public class SalidaService {

    @Autowired
    protected ISalidaRepository salidaRepository;

    @Transactional
    public Salida saveSalida(Derivacion derivacion, Funcionario funcionario) {

        Date fechaSalida = Date.valueOf(LocalDate.now());

        Salida salida = new Salida();
        salida.setDerivacion(derivacion);
        salida.setFuncionario(funcionario);
        salida.setFechaSalida(fechaSalida);

        return salidaRepository.save(salida);
    }
}
