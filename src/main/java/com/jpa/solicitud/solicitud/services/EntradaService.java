package com.jpa.solicitud.solicitud.services;

/* import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.models.dto.EntradaDto;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Entrada;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEntradaRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;

import jakarta.persistence.EntityNotFoundException;

@Service */
public class EntradaService {
/* 
    @Autowired
    private IEntradaRepository entradaRepository;

    @Autowired
    private IDerivacionRepository derivacionRepository;

    @Autowired
    private IFuncionarioRespository funcionarioRespository;


    public Entrada saveEntrada(EntradaDto entradaDto) {
        Derivacion derivacion = derivacionRepository.findDerivacionByIdAndFuncionario(
                entradaDto.getDerivacionId(), entradaDto.getFuncionarioId());

        if (derivacion == null) {
            throw new IllegalArgumentException(
                    "La derivación proporcionada no existe o no está asociada al funcionario");
        }

        
        Funcionario funcionario = new Funcionario();
        funcionario = funcionarioRespository.save(funcionario);
        funcionario.setRut(entradaDto.getRut());

        Entrada entrada = new Entrada();
        entrada.setFechaEntrada(entradaDto.getFechaEntrada());
        entrada.setDerivacion(derivacion);
        entrada.setFuncionario(funcionario);

        return entradaRepository.save(entrada);
    }

    public List<Entrada> findAll() {

        return entradaRepository.findAll();

    }

    public Entrada findById(Long id) {
        return entradaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada con id: " + id));
    }
 */
  
}
