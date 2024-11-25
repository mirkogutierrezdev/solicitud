package com.jpa.solicitud.solicitud.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendEmail(String tipoCorreo, String nombreRemitente, String tipoDocumento, String correoDestino) {
        String apiUrl = "https://appx.laflorida.cl/apilogin/correo.php";

        // Crear un mapa con los datos a enviar
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("tipoCorreo", tipoCorreo);
        requestBody.put("nombreRemitente", nombreRemitente);
        requestBody.put("tipoDocumento", tipoDocumento);
        requestBody.put("correoDestino", correoDestino);

        try {
            // Realizar la solicitud POST
            restTemplate.postForEntity(apiUrl, requestBody, String.class);

          
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la API de correo", e);
        }
    }
}
