package com.jpa.solicitud.solicitud.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpa.solicitud.solicitud.apimodels.SmcFuncionario;
import com.jpa.solicitud.solicitud.apimodels.SmcPersona;

@Service
public class SmcService {

    private final RestTemplate restTemplate;

    public SmcService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SmcFuncionario getFuncionarioByRut(Integer rut) {
        String url = "http://localhost:8080/api/buscar/" + rut;
        ResponseEntity<SmcFuncionario> response = restTemplate.getForEntity(url, SmcFuncionario.class);
        return response.getBody();
    }

    public SmcPersona getPersonaByRut(Integer rut) {
        String url = "http://localhost:8080/api/persona/buscar/" + rut;
        ResponseEntity<SmcPersona> response = restTemplate.getForEntity(url, SmcPersona.class);
        return response.getBody();

    }

}
