package com.jpa.solicitud.solicitud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcDepartamento;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;

import jakarta.transaction.Transactional;

@Service
public class DerivacionService {

    @Autowired
    private IDerivacionRepository derivacionRepository;

    @Autowired
    private ISolicitudRespository solicitudRespository;

    @Autowired
    private SmcService smcService;

  

    public List<Derivacion> findBySolicitudId(Long id) {
        return derivacionRepository.findBySolicitudId(id);
    }

    public List<Derivacion> findNoLeidas(Long depto) {
        return derivacionRepository.findNoLeidas(depto);
    }

    @Transactional
    public void marcarComoNoLeida(Long idDerivacion, Long idSolicutd, Boolean estado) {
        derivacionRepository.marcarComoNoLeida(idDerivacion, idSolicutd, estado);
    }


    public SmcDepartamento saveDerivacion( Long depto){

    

    

        String strDeptoDestino = DepartamentoUtils.determinaDerivacion(depto);

        SmcDepartamento smcDepartamento = smcService.getDepartamento(strDeptoDestino);
       
        return smcDepartamento;


    }



}
