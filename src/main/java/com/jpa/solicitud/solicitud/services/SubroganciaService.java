package com.jpa.solicitud.solicitud.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.SubroganciaDto;
import com.jpa.solicitud.solicitud.models.dto.ViewSubroganciaDto;
import com.jpa.solicitud.solicitud.models.entities.Departamento;
import com.jpa.solicitud.solicitud.models.entities.Departamentos;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Subrogancia;
import com.jpa.solicitud.solicitud.repositories.IDepartamentoRepository;
import com.jpa.solicitud.solicitud.repositories.IDepartamentosRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISubroganciaRepository;
import com.jpa.solicitud.solicitud.utils.DepartamentoUtils;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class SubroganciaService {

        private final ISubroganciaRepository subroganciaRepository;

        private final SmcService smcService;

        private final IFuncionarioRespository funcionarioRespository;

        private final IDepartamentosRepository departamentosRepository;

        private final IDepartamentoRepository departamentoRepository;

        public SubroganciaService(ISubroganciaRepository subroganciaRepository, SmcService smcService,
                        IFuncionarioRespository funcionarioRespository,
                        IDepartamentosRepository departamentosRepository,
                        IDepartamentoRepository departamentoRepository) {
                this.subroganciaRepository = subroganciaRepository;
                this.smcService = smcService;
                this.funcionarioRespository = funcionarioRespository;
                this.departamentosRepository = departamentosRepository;
                this.departamentoRepository = departamentoRepository;
        }

        public Subrogancia saveSubrogancia(SubroganciaDto subroganciaDto) {

                Integer rutJefe = subroganciaDto.getRutJefe();
                Integer rutSubrogante = subroganciaDto.getRutSubrogante();
                LocalDate fechaInicio = subroganciaDto.getFechaInicio();
                LocalDate fechaFin = subroganciaDto.getFechaFin();

                SmcPersona personaJefe = smcService.getPersonaByRut(rutJefe);
                SmcPersona personaSubrogante = smcService.getPersonaByRut(rutSubrogante);

                Funcionario jefeDepto = new Funcionario();
                jefeDepto.setRut(rutJefe);
                jefeDepto.setNombre(StringUtils.buildName(personaJefe.getNombres(), personaJefe.getApellidopaterno(),
                                personaJefe.getApellidopaterno()));

                jefeDepto = funcionarioRespository.save(jefeDepto);

                Funcionario jefeSubrogante = new Funcionario();
                jefeSubrogante.setRut(rutSubrogante);
                jefeSubrogante.setNombre(StringUtils.buildName(personaSubrogante.getNombres(),
                                personaSubrogante.getApellidopaterno(), personaSubrogante.getApellidopaterno()));

                jefeSubrogante = funcionarioRespository.save(jefeSubrogante);

                Departamentos deptos = departamentosRepository.findByDepto(Long.parseLong(subroganciaDto.getDepto()));

                Departamento newDepto = new Departamento();

                newDepto.setDepto(deptos.getDeptoInt());
                newDepto.setDeptoSmc(deptos.getDepto());
                newDepto.setNombre(deptos.getNombreDepartamento());

                newDepto = departamentoRepository.save(newDepto);

                Subrogancia subrogancia = new Subrogancia();
                subrogancia.setJefe(jefeDepto);
                subrogancia.setSubrogante(jefeSubrogante);
                subrogancia.setSubDepartamento(newDepto);
                subrogancia.setFechaInicio(fechaInicio);
                subrogancia.setFechaFin(fechaFin);

                return subroganciaRepository.save(subrogancia);

        }

        public List<SubroganciaDto> getSubroganciasByRutSubrogante(Integer rutSubrogante) {

                List<Subrogancia> subrogancias = subroganciaRepository.findBySubroganteRut(rutSubrogante);

                return subrogancias.stream()
                                .map(sub -> {

                                        SubroganciaDto dto = new SubroganciaDto();
                                        dto.setRutJefe(sub.getJefe().getRut());
                                        dto.setRutSubrogante(sub.getSubrogante().getRut());
                                        dto.setFechaInicio(sub.getFechaInicio());
                                        dto.setFechaFin(sub.getFechaFin());
                                        dto.setDepto(sub.getSubDepartamento().getDeptoSmc().toString());
                                        if (DepartamentoUtils
                                                        .esDireccion(sub.getSubDepartamento().getDepto().toString())
                                                        || DepartamentoUtils.esSubdir(sub.getSubDepartamento()
                                                                        .getDepto().toString())) {
                                                dto.setEsDireccion(true);
                                        } else {
                                                dto.setEsDireccion(false);
                                        }
                                        return dto;

                                }).toList();

        }

        public List<SubroganciaDto> findByDeptoAndFechaInicio(Integer rutSubrogante, LocalDate fechaInicio) {

                List<Subrogancia> subrogancias = subroganciaRepository.findByDeptoAndFechaInicio(rutSubrogante,
                                fechaInicio);

                return subrogancias.stream()
                                .map(sub -> {

                                        SubroganciaDto dto = new SubroganciaDto();
                                        dto.setRutJefe(sub.getJefe().getRut());
                                        dto.setRutSubrogante(sub.getSubrogante().getRut());
                                        dto.setFechaInicio(sub.getFechaInicio());
                                        dto.setFechaFin(sub.getFechaFin());
                                        dto.setDepto(sub.getSubDepartamento().getDeptoSmc().toString());

                                        return dto;

                                }).toList();

        }

        public List<ViewSubroganciaDto> getSubroganciasByRut(Integer rutSubrogante, LocalDate fechaInicio,
                        LocalDate fechaFin) {

                List<Subrogancia> subrogancias = subroganciaRepository.findBySubroganteRutAndDates(rutSubrogante,
                                fechaInicio,
                                fechaFin);

                return subrogancias.stream()
                                .map(sub -> {
                                        // Crear instancia del DTO
                                        ViewSubroganciaDto dto = new ViewSubroganciaDto();

                                        // Validaciones para evitar excepciones por datos nulos
                                        dto.setNombreJefe(
                                                        sub.getJefe() != null ? sub.getJefe().getNombre() : "Sin Jefe");
                                        dto.setNombreSubrogante(
                                                        sub.getSubrogante() != null ? sub.getSubrogante().getNombre()
                                                                        : "Sin Subrogante");
                             
                                     
                                        dto.setId(sub.getId());
                                        dto.setFechaInicio(sub.getFechaInicio());
                                        dto.setFechaFin(sub.getFechaFin());
                                       
                                        // Devolver el DTO
                                        return dto;
                                })
                                .toList();
        }

        public List<ViewSubroganciaDto> getSubroganciasByFechas(LocalDate fechaInicio,
                        LocalDate fechaFin) {

                List<Subrogancia> subrogancias = subroganciaRepository.findByFechaInicioAndFechaFin(
                                fechaInicio,
                                fechaFin);

                return subrogancias.stream()
                                .map(sub -> {
                                        // Crear instancia del DTO
                                        ViewSubroganciaDto dto = new ViewSubroganciaDto();

                                        // Validaciones para evitar excepciones por datos nulos
                                        dto.setNombreJefe(
                                                        sub.getJefe() != null ? sub.getJefe().getNombre() : "Sin Jefe");
                                        dto.setNombreSubrogante(
                                                        sub.getSubrogante() != null ? sub.getSubrogante().getNombre()
                                                                        : "Sin Subrogante");
                                      

                                        // Obtener el primer departamento, si existe
                                       
                                        dto.setNombreDepto(sub.getSubDepartamento().getNombre());

                                        dto.setId(sub.getId());
                                        dto.setFechaInicio(sub.getFechaInicio());
                                        dto.setFechaFin(sub.getFechaFin());
                                     

                                        // Devolver el DTO
                                        return dto;
                                })
                                .toList();
        }

}
