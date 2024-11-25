package com.jpa.solicitud.solicitud.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.jpa.solicitud.solicitud.apimodels.SmcFuncionario;
import com.jpa.solicitud.solicitud.apimodels.SmcPersona;
import com.jpa.solicitud.solicitud.models.dto.AprobacionDto;
import com.jpa.solicitud.solicitud.models.dto.PdfDto;
import com.jpa.solicitud.solicitud.models.entities.Aprobacion;
import com.jpa.solicitud.solicitud.models.entities.Derivacion;
import com.jpa.solicitud.solicitud.models.entities.Estado;
import com.jpa.solicitud.solicitud.models.entities.Funcionario;
import com.jpa.solicitud.solicitud.models.entities.Solicitud;
import com.jpa.solicitud.solicitud.models.entities.Visacion;
import com.jpa.solicitud.solicitud.repositories.IAprobacionRepository;
import com.jpa.solicitud.solicitud.repositories.IDerivacionRepository;
import com.jpa.solicitud.solicitud.repositories.IEstadoRepository;
import com.jpa.solicitud.solicitud.repositories.IFuncionarioRespository;
import com.jpa.solicitud.solicitud.repositories.ISolicitudRespository;
import com.jpa.solicitud.solicitud.repositories.IVisacionRepository;
import com.jpa.solicitud.solicitud.utils.StringUtils;

@Service
public class AprobacionService {

    private final RestTemplate restTemplate;

    private final IAprobacionRepository aprobacionRepository;

    private final IFuncionarioRespository funcionarioRepository;

    private final SmcService smcService;

    private final ISolicitudRespository solicitudRepository;

    private final IEstadoRepository estadoRepository;

    private IDerivacionRepository derivacionRepository;

    private final IVisacionRepository visacionRepository;

    public AprobacionService(IAprobacionRepository aprobacionRepository, IFuncionarioRespository funcionarioRepository,
            SmcService smcService, ISolicitudRespository solicitudRepository, IEstadoRepository estadoRepository,
            IDerivacionRepository derivacionRepository,
            // JsonService jsonService,
            IVisacionRepository visacionRepository, RestTemplate restTemplate) {
        this.aprobacionRepository = aprobacionRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.smcService = smcService;
        this.solicitudRepository = solicitudRepository;
        this.estadoRepository = estadoRepository;
        this.derivacionRepository = derivacionRepository;
        this.visacionRepository = visacionRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public Aprobacion saveAprobacion(AprobacionDto aprobacionDto) {

        Date fechaAprobacion = Date.valueOf(LocalDate.now());
        Integer rut = aprobacionDto.getRut();
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

        Solicitud solicitud = solicitudRepository.findById(aprobacionDto.getSolicitudId()).orElse(null);
        if (solicitud == null) {
            throw new IllegalArgumentException("No se encontró una solicitud con el ID proporcionado");
        }

        Optional<Visacion> optVisacion = visacionRepository.findBySolicitud(solicitud);

        if (optVisacion.isEmpty()) { // Mejor usar isEmpty() para verificar si está vacío
            
             Visacion visacion = new Visacion();

            visacion.setFechaVisacion(LocalDate.now());
            visacion.setFuncionario(funcionario);
            visacion.setSolicitud(solicitud);
            visacion.setTransaccion(LocalDateTime.now());
            visacionRepository.save(visacion);
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

        Estado estado = estadoRepository.findById(codEstado).orElseGet(() -> {
            Estado nuevoEstado = new Estado();
            nuevoEstado.setId(codEstado);
            nuevoEstado.setNombre(estadoDto);
            return estadoRepository.save(nuevoEstado);
        });
        solicitud.setEstado(estado);

        Aprobacion aprobacion = new Aprobacion();
        aprobacion.setFuncionario(funcionario); // Asignar el funcionario guardado
        aprobacion.setSolicitud(solicitud);
        aprobacion.setFechaAprobacion(fechaAprobacion);

        // Guardar la aprobación sin la URL del PDF
        Aprobacion savedAprobacion = aprobacionRepository.save(aprobacion);

        // Llamar al método para generar el PDF y obtener la URL después de guardar la
        // aprobación
        String url = preparePdf(solicitud);

        // Asignar la URL generada a la aprobación ya guardada
        savedAprobacion.setUrlPdf(url);

        // Actualizar la aprobación con la URL del PDF
        return aprobacionRepository.save(savedAprobacion); // Se guarda nuevamente con la URL
    }

    public Aprobacion servGetAprobacionBySolicitud(Long solicitudId) {
        return aprobacionRepository.findBySolicitudId(solicitudId);
    }

    public Solicitud servGetSolicitudById(Long solicitudId) {
        return solicitudRepository.findById(solicitudId).orElse(null);
    }

    public String preparePdf(Solicitud solicitud) {
        // Convertir java.sql.Date a java.time.LocalDate
        LocalDateTime fechaInicio = solicitud.getFechaInicio();
        LocalDateTime fechaTermino = solicitud.getFechaFin();

        Integer rut = solicitud.getFuncionario().getRut();
        SmcFuncionario funcionario = smcService.getFuncionarioByRut(rut);
        SmcPersona persona = smcService.getPersonaByRut(rut);

        String departamento = funcionario.getDepartamento().getNombreDepartamento();
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

        // Obtener rut visador (jefe)
        Visacion visacion = visacionRepository.findBySolicitudId(solicitud.getId());
        String rutJefe = visacion.getFuncionario().getRut().toString();
        String nombreJefe = visacion.getFuncionario().getNombre();

        Aprobacion aprobacion = servGetAprobacionBySolicitud(solicitud.getId());

        // Puedes obtener los datos del director desde el `Funcionario` relacionado con
        // la solicitud, o manejarlo manualmente
        String rutDirector = aprobacion.getFuncionario().getRut().toString();
        String nombreDirector = aprobacion.getFuncionario().getNombre();

        // Crear el objeto PdfDto
        PdfDto pdfDto = new PdfDto();
        pdfDto.setNroIniDia(diaInicio);
        pdfDto.setMesIni(mesInicio);
        pdfDto.setNroFinDia(diaFin);
        pdfDto.setMesFin(mesFin);
        pdfDto.setRut(String.valueOf(rut));
        pdfDto.setVrut(persona.getVrut());
        pdfDto.setTelefono(Integer.toString(persona.getTelefono()));
        pdfDto.setDiasTomados(solicitud.getDuracion().toString());
        pdfDto.setPaterno(persona.getApellidopaterno());
        pdfDto.setMaterno(persona.getApellidomaterno());
        pdfDto.setNombres(persona.getNombres());
        pdfDto.setEscalafon(escalafon);
        pdfDto.setGrado(String.valueOf(grado));
        pdfDto.setDepto(departamento);
        pdfDto.setNombreSolicitud(solicitud.getTipoSolicitud().getNombre());
        if (solicitud.getTipoSolicitud().getNombre().equals("ADMINISTRATIVO")) {
            if (fechaTermino.getHour() == 12 && fechaTermino.getMinute() == 0) {
                pdfDto.setJornada("AM");
            } else if (fechaTermino.getHour() == 17 && fechaTermino.getMinute() == 30) {
                pdfDto.setJornada("PM");
            } else if (fechaTermino.getHour() == 0 && fechaTermino.getMinute() == 0) {
                pdfDto.setJornada("N/A");
            }

        }
        pdfDto.setRutJefe(rutJefe);
        pdfDto.setNombreJefe(nombreJefe);
        pdfDto.setRutDirector(rutDirector);
        pdfDto.setNombreDirector(nombreDirector);
        pdfDto.setTipoSolicitud(solicitud.getTipoSolicitud().getId());

        // Realizar la solicitud POST a la API externa
        String apiUrl = "https://appx.laflorida.cl/firma-docs/index2api.php";
        String url = "https://appx.laflorida.cl/firma-docs/";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, pdfDto, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Devuelve la ruta del documento si la respuesta es exitosa

                String responseString = response.getBody();

                return url + responseString;
            } else {
                throw new RuntimeException("Error al generar el documento PDF: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la API de generación de PDF", e);
        }
    }

    public List<Aprobacion> saveAprobaciones(List<AprobacionDto> aprobaciones) {
        return aprobaciones.stream()
                .map(aprobacionDto -> {
                    try {
                        return saveAprobacion(aprobacionDto);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Error al guardar la aprobación: " + e.getMessage());
                    }
                })
                .toList();
    }

    public List<Aprobacion> findAll() {

        return aprobacionRepository.findAll();
    }

    public List<Aprobacion> findAprobacionWithSolicitud() {
        return aprobacionRepository.findAllWithSolicitud();
    }

    public List<Aprobacion> getAllWithoutDecreto() {
        return aprobacionRepository.findAllWithoutDecreto();
    }
}