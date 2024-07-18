package com.jpa.solicitud.solicitud.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.SolicitudDto;
import com.jpa.solicitud.solicitud.models.dto.SolicitudWithDerivacionesDTO;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Entrada;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Salida;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.TipoSolicitud;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEntradaRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISalidaRepository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.repositories.ITipoSolicitudRepository;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class SolicitudService {

    // *inyección de dependencias de las interfaces repository */

    @Autowired
    private IFuncionarioRespository funcionarioRespository;

    @Autowired
    private ITipoSolicitudRepository tipoSolicitudRepository;

    @Autowired
    private IEstadoRepository estadoRepository;

    @Autowired
    private ISolicitudRespository solicitudRespository;

    @Autowired
    private IDerivacionRepository derivacionRepository;

    @Autowired
    private IDepartamentoRepository departamentoRepository;

    @Autowired
    private IEntradaRepository entradaRepository;

    @Autowired
    private ISalidaRepository salidaRepository;

    @Autowired SmcService smcService;

    @Autowired
    private IDepartamentosRepository departamentosRepository;

/*     @Autowired
    private SmcService smcService;
 */

    // *RestTemplate para la api a smc */
    public SolicitudService(RestTemplate restTemplate) {
    }

    @Transactional
    public Solicitud saveSolicitud(SolicitudDto solicitudDto) {
        // Crear y persistir el funcionario
        SmcPersona persona = smcService.getPersonaByRut(solicitudDto.getRut());
       
        Funcionario funcionario = new Funcionario();
        funcionario.setRut(solicitudDto.getRut());
        funcionario.setNombre(StringUtils.buildName(persona.getNombres(), persona.getApellidopaterno(), persona.getApellidomaterno()));
        
        funcionario = funcionarioRespository.save(funcionario);
        

        // Obtener el tipo de solicitud por nombre y lanzar excepción si no se encuentra
        Long idSol = tipoSolicitudRepository.findIdByNombre(solicitudDto.getTipoSolicitud());
        if (idSol == null) {
            throw new IllegalArgumentException("Tipo de solicitud no encontrado");
        }
        TipoSolicitud tipoSol = tipoSolicitudRepository.findById(idSol)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de solicitud no encontrado"));

        // Obtener el estado por nombre y lanzar excepción si no se encuentra
        Long idEstado = estadoRepository.findIdByNombre(solicitudDto.getEstado());
        if (idEstado == null) {
            throw new IllegalArgumentException("Estado no encontrado");
        }
        Estado estado = estadoRepository.findById(idEstado)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));

        // Crear y persistir la solicitud
        Solicitud solicitud = new Solicitud();
        solicitud.setFuncionario(funcionario);
        solicitud.setTipoSolicitud(tipoSol);
        solicitud.setFechaSolicitud(solicitudDto.getFechaSol());
        solicitud.setFechaInicio(solicitudDto.getFechaInicio());
        solicitud.setFechaFin(solicitudDto.getFechaFin());
        solicitud.setEstado(estado);
        solicitud = solicitudRespository.save(solicitud);

        Departamento departamento = new Departamento();
        
        Departamentos depto = departamentosRepository.findByDepto(solicitudDto.getDepto());

        departamento.setDepto(depto.getDeptoInt());
        departamento.setDeptoSmc(depto.getDepto());
        departamento.setNombre(depto.getNombre_departamento());
        departamento = departamentoRepository.save(departamento);

        // Crear y persistir la derivación
        Derivacion derivacion = new Derivacion();
        derivacion.setFechaDerivacion(solicitudDto.getFechaDer());
        derivacion.setLeida(false);
        derivacion.setDepartamento(departamento);
        derivacion.setSolicitud(solicitud);
      
        derivacion.setComentarios("Prueba de derivacion");
        derivacion.setFuncionario(funcionario);
        derivacionRepository.save(derivacion);

        return solicitud;
    }

    public List<Solicitud> findAll() {
        return solicitudRespository.findAll();
    }

    

    public List<SolicitudWithDerivacionesDTO> getSolicitudesWithDerivacionesByDepartamento(Long departamentoId) {

        Departamentos departamentos = departamentosRepository.findByDepto(departamentoId);
        // Obtener todas las derivaciones del departamento
        List<Derivacion> derivaciones = derivacionRepository.findByDepartamentoDepto(departamentos.getDeptoInt());
    
        // Obtener todas las solicitudes a partir de las derivaciones
        List<Solicitud> solicitudes = derivaciones.stream()
                                                  .map(Derivacion::getSolicitud)
                                                  .distinct()
                                                  .collect(Collectors.toList());
    
        // Crear el DTO para cada solicitud
        return solicitudes.stream().map(solicitud -> {
            List<Derivacion> derivacionesSolicitud = derivacionRepository.findBySolicitudId(solicitud.getId());
            List<Entrada> entradas = derivacionesSolicitud.stream()
                                                          .flatMap(derivacion -> entradaRepository.findByDerivacionId(derivacion.getId()).stream())
                                                          .collect(Collectors.toList());
            List<Salida> salidas = derivacionesSolicitud.stream()
                                                        .flatMap(derivacion -> salidaRepository.findByDerivacion_Id(derivacion.getId()).stream())
                                                        .collect(Collectors.toList());
    
            SolicitudWithDerivacionesDTO dto = new SolicitudWithDerivacionesDTO();
            dto.setSolicitud(solicitud);
            dto.setDerivaciones(derivacionesSolicitud);
            dto.setEntradas(entradas);
            dto.setSalidas(salidas);
    
            return dto;
        }).collect(Collectors.toList());
    }

    
}
