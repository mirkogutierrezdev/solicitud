package com.jpa.solicitud.solicitud.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpa.solicitud.solicitud.apimodels.SmcDepartamento;
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

    public boolean isJefe(Integer rut) {
        String url = "http://localhost:8080/api/smc/funcionario/esjefe/" + rut;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return response.getBody() != null && response.getBody(); // Retornar true si es jefe
    }
}
