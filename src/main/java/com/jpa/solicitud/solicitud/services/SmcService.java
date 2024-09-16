package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpa.solicitud.solicitud.apimodels.SmcDepartamento;
import com.jpa.solicitud.solicitud.apimodels.SmcFeriado;
import com.jpa.solicitud.solicitud.apimodels.SmcFuncionario;
import com.jpa.solicitud.solicitud.apimodels.SmcPersona;

@Service
public class SmcService {

    private final RestTemplate restTemplate;

    public SmcService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SmcFuncionario getFuncionarioByRut(Integer rut) {

        final String url = "http://localhost:8080/api/smc/funcionario/buscar/" + rut;
        ResponseEntity<SmcFuncionario> response = restTemplate.getForEntity(url, SmcFuncionario.class);
        return response.getBody();
    }

    public SmcPersona getPersonaByRut(Integer rut) {
        String url = "http://localhost:8080/api/smc/persona/buscar/" + rut;
        ResponseEntity<SmcPersona> response = restTemplate.getForEntity(url, SmcPersona.class);
        return response.getBody();

    }

    public SmcDepartamento getDepartamento(String depto) {
        String url = "http://localhost:8080/api/smc/funcionario/depto/" + depto;
        ResponseEntity<SmcDepartamento> response = restTemplate.getForEntity(url, SmcDepartamento.class);
        return response.getBody();

    }

    public List<SmcDepartamento> getDepartamentos() {
        String url = "http://localhost:8080/api/departamentos/list";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<SmcDepartamento>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SmcDepartamento>>() {
                });

        return response.getBody();
    }

    public boolean isJefe(Integer rut) {
        String url = "http://localhost:8080/api/smc/funcionario/esjefe/" + rut;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return response.getBody() != null && Boolean.TRUE.equals(response.getBody()); // Retornar true si es jefe
    }

    public List<SmcFeriado> getFeriados(Date fechaInicio, Date fechaTermino) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strFechaInicio = dateFormat.format(fechaInicio);
        String strFechaTermino = dateFormat.format(fechaTermino);

        String url = "http://localhost:8080/api/smc/feriados/calcular?fechaInicio=" + strFechaInicio + "&fechaTermino="
                + strFechaTermino;

        ResponseEntity<List<SmcFeriado>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SmcFeriado>>() {
                });

        return response.getBody();
    }
}
