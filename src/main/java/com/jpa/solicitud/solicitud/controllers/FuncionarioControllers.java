package com.jpa.solicitud.solicitud.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpa.solicitud.solicitud.apimodels.SmcFuncionario;
import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.services.SmcService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/smc")
public class FuncionarioControllers {

    @Autowired
    private SmcService smcService;

    @GetMapping("/byRut/{rut}")
    public ResponseEntity<SmcFuncionario> getFuncionarioByRut(@PathVariable Integer rut) {
        SmcFuncionario funcionario = smcService.getFuncionarioByRut(rut);
        if (funcionario != null) {
            return ResponseEntity.ok(funcionario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byRut/person/{rut}")
    public ResponseEntity<SmcPersona> getPersonaByRut(@PathVariable Integer rut) {
        SmcPersona funcionario = smcService.getPersonaByRut(rut);
        if (funcionario != null) {
            return ResponseEntity.ok(funcionario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
