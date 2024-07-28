package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.solicitud.solicitud.apimodels.SmcFuncionario;
import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.AprobacionDto;
import com.jpa.solicitud.solicitud.models.dto.PdfDto;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.repositories.IAprobacionRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class AprobacionService {

    @Autowired
    private IAprobacionRepository aprobacionRepository;

    @Autowired
    private IFuncionarioRespository funcionarioRepository;

    @Autowired
    private SmcService smcService;

    @Autowired
    private ISolicitudRespository solicitudRepository;

    @Autowired
    private IEstadoRepository estadoRepository;

    @Autowired
    private IDerivacionRepository derivacionRepository;

    @Autowired
    private JsonService jsonService;

    @Transactional
    public Aprobacion saveAprobacion(AprobacionDto aprobacionDto) throws Exception {
        if (aprobacionDto == null) {
            throw new IllegalArgumentException("El objeto AprobacionDto no puede ser null");
        }

        Integer rut = aprobacionDto.getRutFuncionario();
        SmcPersona persona = smcService.getPersonaByRut(rut);
        if (persona == null) {
            throw new IllegalArgumentException("No se encontró un funcionario con el RUT proporcionado");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setRut(persona.getRut());
        funcionario.setNombre(
                StringUtils.buildName(persona.getNombres(), persona.getApellidopaterno(),
                        persona.getApellidomaterno()));
        funcionario = funcionarioRepository.save(funcionario); // Guardar el funcionario antes de asignarlo

        Solicitud solicitud = solicitudRepository.findById(aprobacionDto.getIdSolicitud()).orElse(null);
        if (solicitud == null) {
            throw new IllegalArgumentException("No se encontró una solicitud con el ID proporcionado");
        }

        List<Derivacion> derivaciones = derivacionRepository.findBySolicitudId(solicitud.getId());

        if (derivaciones.isEmpty()) {
            throw new IllegalArgumentException("No hay derivaciones asociadas a la solicitud proporcionada.");
        }

        Derivacion ultimaDerivacion = derivaciones.stream()
                .max(Comparator.comparing(Derivacion::getId))
                .orElseThrow(() -> new IllegalArgumentException("No se pudo encontrar la última derivación."));

        ultimaDerivacion.setLeida(true);

        String estadoDto = aprobacionDto.getEstado();

        Long codEstado = estadoRepository.findIdByNombre(estadoDto);

        Estado estado = new Estado();
        estado.setId(codEstado);
        estado.setNombre(estadoDto);
        estado = estadoRepository.save(estado);

        solicitud.setEstado(estado);

        Aprobacion aprobacion = new Aprobacion();
        aprobacion.setFuncionario(funcionario); // Asignar el funcionario guardado
        aprobacion.setSolicitud(solicitud);
        aprobacion.setFechaAprobacion(aprobacionDto.getFechaAprobacion());

        // Generar el PDF y guardar en la base de datos
        byte[] pdfBytes = preparePdf(solicitud);
        aprobacion.setPdf(pdfBytes);

        // Guardar el objeto Aprobacion en el repositorio
        return aprobacionRepository.save(aprobacion);
    }

    public Aprobacion servGetAprobacionBySolicitud(Long solicitudId) {
        return aprobacionRepository.findBySolicitudId(solicitudId);
    }

    public Solicitud servGetSolicitudById(Long solicitudId) {
        return solicitudRepository.findById(solicitudId).orElse(null);
    }

    public byte[] preparePdf(Solicitud solicitud) throws Exception {
        Date sqlFechaInicio = solicitud.getFechaInicio();
        Date sqlFechaTermino = solicitud.getFechaFin();

        // Convertir java.sql.Date a java.time.LocalDate
        LocalDate fechaInicio = sqlFechaInicio.toLocalDate();
        LocalDate fechaTermino = sqlFechaTermino.toLocalDate();

        Integer rut = solicitud.getFuncionario().getRut();
        SmcFuncionario funcionario = smcService.getFuncionarioByRut(rut);
        SmcPersona persona = smcService.getPersonaByRut(rut);

        String departamento = funcionario.getDepartamento().getNombre_departamento();
        String escalafon = funcionario.getContrato().getEscalafon();
        Integer grado = funcionario.getContrato().getGrado();

        // Obtener el día y mes inicial
        String diaInicio = String.valueOf(fechaInicio.getDayOfMonth());
        String mesInicio = fechaInicio.getMonth().getDisplayName(TextStyle.FULL,
                new Locale.Builder().setLanguage("es").setRegion("ES").build());

        // Obtener el día y mes final
        String diaFin = String.valueOf(fechaTermino.getDayOfMonth());
        String mesFin = fechaTermino.getMonth().getDisplayName(TextStyle.FULL,
                new Locale.Builder().setLanguage("es").setRegion("ES").build());

        PdfDto pdfDto = new PdfDto();
        pdfDto.setNroIniDia(diaInicio);
        pdfDto.setMesIni(mesInicio);
        pdfDto.setNroFinDia(diaFin);
        pdfDto.setMesFin(mesFin);
        pdfDto.setRut(String.valueOf(rut));
        pdfDto.setFono("123456789");
        pdfDto.setDiasTomados("5");
        pdfDto.setPaterno(persona.getApellidopaterno());
        pdfDto.setMaterno(persona.getApellidomaterno());
        pdfDto.setNombres(persona.getNombres());

        pdfDto.setEscalafon(escalafon);
        pdfDto.setGrado(String.valueOf(grado));
        pdfDto.setDepto(departamento);

        // Aquí puedes continuar con la lógica de generación de PDF usando estos valores

        return jsonService.generateReport(pdfDto); // Llamar al servicio de generación de PDF
    }
}
